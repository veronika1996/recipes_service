package com.recepies_service.entity;

import com.recepies_service.dto.IngredientDTO;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class IngredientClient {

  private final RestTemplate restTemplate;

  @Value("${ingredients.service.url}")
  private String ingredientsServiceUrl;

  public IngredientClient(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public List<IngredientDTO> getIngredientsByIds(List<Long> ids) {
    IngredientDTO[] response = restTemplate.postForObject(
        ingredientsServiceUrl + "/ingredients/byIds", ids, IngredientDTO[].class
    );
    return Arrays.asList(response);
  }

  public IngredientDTO getIngredientByNameAndUsername(String name, String username) {
    IngredientDTO response = restTemplate.getForObject(
        ingredientsServiceUrl + "/ingredients?name=" + name + "&username=" + username, IngredientDTO.class
    );
    return response;
  }
}