package com.sptrainer.grouptrainerservice.transport;

import com.sptrainer.domain.model.Token;
import com.sptrainer.domain.util.GeneralUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class WebUtil {

    public static ResponseEntity<String> callGet(WebClient.Builder client, String url) throws Exception {
        Mono<ResponseEntity<String>> mono = client.build().get()
                .uri(url)
                .header("Content-Type", "application/json")
                .exchange()
                .flatMap(resp -> resp.toEntity(String.class));
        ResponseEntity<String> resp = mono.block();
        return resp;
    }

    public static ResponseEntity<String> callPost(WebClient.Builder client, String url, Object obj) throws Exception {
        Mono<ResponseEntity<String>> mono = client.build().post()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json")
                .body(BodyInserters.fromObject(obj))
                .exchange()
                .flatMap(resp -> resp.toEntity(String.class));
        ResponseEntity<String> resp = mono.block();
        return resp;
    }

    public static ResponseEntity<String> callPut(WebClient.Builder client, String url, Object obj) throws Exception {
        Mono<ResponseEntity<String>> mono = client.build().put()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json")
                .body(BodyInserters.fromObject(obj))
                .exchange()
                .flatMap(resp -> resp.toEntity(String.class));
        ResponseEntity<String> resp = mono.block();
        return resp;
    }

    public static ResponseEntity<String> callPutWithOutBody(WebClient.Builder client, String url) throws Exception {
        Mono<ResponseEntity<String>> mono = client.build().put()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json")
                .exchange()
                .flatMap(resp -> resp.toEntity(String.class));
        ResponseEntity<String> resp = mono.block();
        return resp;
    }

    public static ResponseEntity<String> callDelete(WebClient.Builder client, String url) throws Exception {
        Mono<ResponseEntity<String>> mono = client.build().delete()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json")
                .exchange()
                .flatMap(resp -> resp.toEntity(String.class));
        ResponseEntity<String> resp = mono.block();
        return resp;
    }

    public static List<Object> callGetList(WebClient.Builder client, String url, Class type) throws Exception {
        Flux<Object> flux = client.build().get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json")
                .retrieve()
                .bodyToFlux(type);
        return flux.collectList().block();
    }

    public static ResponseEntity response(ResponseEntity<String> resp, Class type) throws Exception {
        if (resp.getStatusCodeValue() == 200) {
            return ResponseEntity.status(resp.getStatusCode()).body(GeneralUtil.mapper.readValue(resp.getBody(), type));
        } else {
            return ResponseEntity.status(resp.getStatusCode()).body(resp.getBody());
        }
    }

    public static ResponseEntity responseList(ResponseEntity<String> resp, Class type) throws Exception {
        if (resp.getStatusCodeValue() == 200) {
            return ResponseEntity.status(resp.getStatusCode()).body(GeneralUtil.mapper.readValue(resp.getBody(), GeneralUtil.mapper.getTypeFactory().constructCollectionType(List.class, type)));
        } else {
            return ResponseEntity.status(resp.getStatusCode()).body(resp.getBody());
        }
    }

    public static ResponseEntity responseError(String error) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    public static ResponseEntity responseIntValue(ResponseEntity<String> resp) throws Exception {
        if (resp.getStatusCodeValue() == 200) {
            return ResponseEntity.status(resp.getStatusCode()).body(Integer.parseInt(resp.getBody()));
        } else {
            return ResponseEntity.status(resp.getStatusCode()).body(resp.getBody());
        }
    }

}
