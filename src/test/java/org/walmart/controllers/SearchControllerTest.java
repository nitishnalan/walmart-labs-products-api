package org.walmart.controllers;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.walmart.models.SearchAndFilterRequest;
import org.walmart.repository.ProductRepository;

import java.util.Collections;
import java.util.Map;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@Profile("test")
public class SearchControllerTest {

    private MockMvc mockMvc;

    private static final Logger logger = LoggerFactory.getLogger(SearchClientController.class);

    @Mock
    ProductRepository productRepository;


    SearchAndFilterRequest searchAndFilterRequestObj;

    @InjectMocks
    SearchClientController searchClientController;


    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(searchClientController).build();

        searchAndFilterRequestObj = new SearchAndFilterRequest("",(float)0,(float)0,
                                0,0,0,0, true);
    }

    @Test
    public void whenSearchClient_withCorrectInStockParameter_thenOkResponse(){

        try{
            ObjectMapper objectMapper = new ObjectMapper();

            when(productRepository.getSearchAndFilteredProducts(searchAndFilterRequestObj)).thenReturn(Collections.emptyList());

            MultiValueMap multiValueMapSearachAndFilterRequest = new LinkedMultiValueMap<String, String>();
            Map<String, String> maps = objectMapper.convertValue(searchAndFilterRequestObj, new TypeReference<Map<String, String>>() {});
            multiValueMapSearachAndFilterRequest.setAll(maps);

            mockMvc.perform(get("/searchClient").params(multiValueMapSearachAndFilterRequest).contentType(MediaType.APPLICATION_JSON_UTF8)).andDo(print()).andExpect(MockMvcResultMatchers.status().isOk());

            verify(productRepository).getSearchAndFilteredProducts(refEq(searchAndFilterRequestObj));

        }catch (Exception e){
            logger.error(e.toString());
        }

    }

    @Test
    public void whenSearchClient_withInCorrectInStockParameter_thenBadResponse(){

        try{
            mockMvc.perform(get("/searchClient").param("inStock","yes").contentType(MediaType.APPLICATION_JSON_UTF8)).
                    andDo(print()).andExpect(MockMvcResultMatchers.status().isBadRequest());

        }catch (Exception e){
            logger.error(e.toString());
        }

    }

    @Test
    public void whenSearchClient_withInCorrect_minPrice_Parameter_thenBadResponse(){

        try{
            mockMvc.perform(get("/searchClient").param("minPrice","-1").contentType(MediaType.APPLICATION_JSON_UTF8)).
                    andDo(print()).andExpect(MockMvcResultMatchers.status().isBadRequest());

        }catch (Exception e){
            logger.error(e.toString());
        }

    }

    @Test
    public void whenSearchClient_withCorrect_minPrice_Parameter_thenOkResponse(){

        try{
            mockMvc.perform(get("/searchClient").param("minPrice","5").contentType(MediaType.APPLICATION_JSON_UTF8)).
                    andDo(print()).andExpect(MockMvcResultMatchers.status().isOk());

        }catch (Exception e){
            logger.error(e.toString());
        }

    }

    @Test
    public void whenSearchClient_withCorrect_minPrice_andInCorrect_maxPrice_Parameter_thenBadResponse(){

        try{
            mockMvc.perform(get("/searchClient").param("minPrice","5")
                    .param("maxPrice","-1").contentType(MediaType.APPLICATION_JSON_UTF8)).
                    andDo(print()).andExpect(MockMvcResultMatchers.status().isBadRequest());

        }catch (Exception e){
            logger.error(e.toString());
        }

    }

    @Test
    public void whenSearchClient_inCorrect_minReviewRating_Parameter_thenBadResponse(){

        try{
            mockMvc.perform(get("/searchClient").param("minReviewRating","-1").contentType(MediaType.APPLICATION_JSON_UTF8)).
                    andDo(print()).andExpect(MockMvcResultMatchers.status().isBadRequest());

        }catch (Exception e){
            logger.error(e.toString());
        }

    }

    @Test
    public void whenSearchClient_inCorrect_maxReviewRating_Parameter_thenBadResponse(){

        try{
            mockMvc.perform(get("/searchClient").param("maxReviewRating","6").contentType(MediaType.APPLICATION_JSON_UTF8)).
                    andDo(print()).andExpect(MockMvcResultMatchers.status().isBadRequest());

        }catch (Exception e){
            logger.error(e.toString());
        }

    }

    @Test
    public void whenSearchClient_inCorrect_minReviewCount_Parameter_thenBadResponse(){

        try{
            mockMvc.perform(get("/searchClient").param("minReviewCount","-1").contentType(MediaType.APPLICATION_JSON_UTF8)).
                    andDo(print()).andExpect(MockMvcResultMatchers.status().isBadRequest());

        }catch (Exception e){
            logger.error(e.toString());
        }

    }

    @Test
    public void whenSearchClient_inCorrect_maxReviewCount_Parameter_thenBadResponse(){

        try{
            mockMvc.perform(get("/searchClient").param("maxReviewCount","-1").contentType(MediaType.APPLICATION_JSON_UTF8)).
                    andDo(print()).andExpect(MockMvcResultMatchers.status().isBadRequest());

        }catch (Exception e){
            logger.error(e.toString());
        }

    }

}
