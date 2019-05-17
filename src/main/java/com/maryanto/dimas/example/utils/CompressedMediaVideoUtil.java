package com.maryanto.dimas.example.utils;

import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFmpegUtils;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;
import net.bramp.ffmpeg.progress.Progress;
import net.bramp.ffmpeg.progress.ProgressListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class CompressedMediaVideoUtil {

    FFmpeg ffmpeg = null;
    FFprobe ffprobe = null;

    public String getHomeDirectory() {
        return System.getProperty("user.home");
    }

    public CompressedMediaVideoUtil(
            @Value("${server.compression.ffmpeg}") String ffmpegPath,
            @Value("${server.compression.ffprobe}") String ffprobePath) throws IOException {
        ffmpeg = new FFmpeg();
        if (!StringUtils.isEmpty(ffmpegPath))
            ffmpeg = new FFmpeg(ffmpegPath);

        ffprobe = new FFprobe();
        if (!StringUtils.isEmpty(ffprobePath))
            ffprobe = new FFprobe(ffprobePath);

    }

    public String convertVideo(String fileName, String format) throws IOException {
        String dirPath = new StringBuilder(getHomeDirectory()).append(File.separator)
                .append("Videos").append(File.separator)
                .append("compressed").append(File.separator)
                .append(LocalDate.now()).toString();

        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String outputLocation = new StringBuilder(dirPath)
                .append(File.separator).append(UUID.randomUUID().toString()).append(".").append(format).toString();
        FFmpegProbeResult input = ffprobe.probe(fileName);
        FFmpegBuilder builder = new FFmpegBuilder()
                .setInput(input).overrideOutputFiles(true)

                .addOutput(outputLocation)
                .setFormat(format)
                .disableSubtitle()

//                config audio
                .setAudioChannels(1)
                .setAudioCodec("aac")
                .setAudioSampleRate(48_000)
                .setAudioBitRate(32_768)

//                config video
                .setVideoCodec("libx264")
                .setVideoFrameRate(24, 1)
                .setVideoResolution(640, 480)

                .setStrict(FFmpegBuilder.Strict.EXPERIMENTAL)
                .done();

        FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
        executor.createJob(builder, new ProgressListener() {

            final double duration_ns = input.getFormat().duration * TimeUnit.SECONDS.toNanos(1);

            @Override
            public void progress(Progress progress) {
                double percentage = progress.out_time_ns / duration_ns;

                if (progress.status.equals(Progress.Status.END)) {
                    log.info("filename: {} completed!", input.getFormat().filename);
                } else {
                    log.info("filename: {} -> {} status: {} time: {} ms",
                            input.getFormat().filename,
                            String.format("[%.0f%%]", (percentage * 100)),
                            progress.status,
                            FFmpegUtils.toTimecode(progress.out_time_ns, TimeUnit.NANOSECONDS)
                    );
                }
            }
        }).run();
        return outputLocation;
    }


}
