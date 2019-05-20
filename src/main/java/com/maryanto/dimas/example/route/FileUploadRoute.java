package com.maryanto.dimas.example.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileUploadRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("sql:select * from media where is_write = false order by created_date?onConsume=update media set is_write = true where id = :#id")
                .log("${body}")
                .to("bean:converterVideoExecute?method=generateThumbnail")
                .to("bean:converterVideoExecute?method=scaleVideo");
    }
}
