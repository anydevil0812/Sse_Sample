package com.spring.pando;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j 
public class SseService {
    
     // 주로 순회가 일어나는 용도로 사용할 때는 안전한 스레드 처리를 위해 CopyOnWriteArrayList를 사용
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    Random random = new Random();

    public void addEmitter(SseEmitter emitter) {

        System.out.println("------------------2");
        emitters.add(emitter);
        log.info("new emitter added: {}", emitter);  
        log.info("emitter list size: {}", emitters.size()); 
        emitter.onCompletion(() -> {  
            log.info("onCompletion callback");  
            emitters.remove(emitter);    // 만료되면 리스트에서 삭제
        });  
        emitter.onTimeout(() -> {  
            log.info("onTimeout callback");  
            emitter.complete();  
        });  
    }

    // 1초마다 해당 메소드가 자동으로 실행되도록 지정 (fixedRate 단위=밀리초)
    @Scheduled(fixedRate = 1000)
    public void sendEvents() {
        for (SseEmitter emitter : emitters) {

            System.out.println("------------------3");

            try {
                int num = random.nextInt(10);
                System.out.println("------------------3-1 : " + num);
                String data = " Hello, World!" + num;
                int size = data.getBytes().length;
                emitter.send(data + " / Data Byte Size : " + size);
                log.info("send Hello, World! " + num); 
            } catch (IOException e) {
                emitters.remove(emitter);
                emitter.complete();

                System.out.println("------------------3-2");
                System.out.println(emitter);
            }
        }
    }
}
