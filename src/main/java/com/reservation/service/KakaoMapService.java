package com.reservation.service;

import com.reservation.exception.KakaoException;
import com.reservation.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

@Service
public class KakaoMapService {

    private static final String GEOCODING_API_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    @Value("${spring.kakao.rest-api-key}")
    private String API_KEY;

    public double[] getDistanceFromAddress(String address) {
        String url = GEOCODING_API_URL + "?query=" + address;

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + API_KEY);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        String response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();

        JSONObject jsonResponse = new JSONObject(response);
        JSONArray documents = jsonResponse.getJSONArray("documents");

        if (!documents.isEmpty()) {
            JSONObject location = documents.getJSONObject(0);
            double lat = location.getDouble("y");
            double lng = location.getDouble("x");
            return new double[]{lat, lng};
        }

        throw new KakaoException(ErrorCode.ADDRESS_NOT_FOUND);
    }

}
