package co.kr.pando.pando;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api")
public class MainController {

    @Autowired
    private SseService sseService;

    // UTF-8 데이터만 보낼 수 있음, 바이너리 데이터 지원 x
    @GetMapping(path = "/emitter", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {

        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        System.out.println("------------------1");

        sseService.addEmitter(emitter);
        sseService.sendEvents();
        return emitter;
    }

}