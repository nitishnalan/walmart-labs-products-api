package org.walmart.controllers;


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
import org.walmart.models.SearchAndFilterRequest;
import org.walmart.repository.ProductRepository;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    public void whenSearchClient_withCorrectInStockParameter_thenOkResponse() throws Exception{

        try{
            ObjectMapper objectMapper = new ObjectMapper();

            when(productRepository.getSearchAndFilteredProducts(searchAndFilterRequestObj)).thenReturn(Collections.emptyList());
//            mockMvc.perform(post("/searchClient").content("{\"inStock\":\"true\"}").contentType(MediaType.APPLICATION_JSON_UTF8)).
//                    andDo(print()).andExpect(MockMvcResultMatchers.status().isOk());

            mockMvc.perform(post("/searchClient").content(objectMapper.writeValueAsString(searchAndFilterRequestObj)).contentType(MediaType.APPLICATION_JSON_UTF8)).
                    andDo(print()).andExpect(MockMvcResultMatchers.status().isOk());

            verify(productRepository).getSearchAndFilteredProducts(refEq(searchAndFilterRequestObj));

        }catch (Exception e){
            logger.error(e.toString());
        }

    }

    @Test
    public void whenSearchClient_withInCorrectInStockParameter_thenBadResponse() throws Exception{

        try{
            mockMvc.perform(post("/searchClient").content("{\"inStock\":\"yes\"}").contentType(MediaType.APPLICATION_JSON_UTF8)).
                    andDo(print()).andExpect(MockMvcResultMatchers.status().isBadRequest());

        }catch (Exception e){
            logger.error(e.toString());
        }

    }

    @Test
    public void whenSearchClient_withInCorrect_minPrice_Parameter_thenBadResponse() throws Exception{

        try{
            mockMvc.perform(post("/searchClient").content("{\"minPrice\":\"-1\"}").contentType(MediaType.APPLICATION_JSON_UTF8)).
                    andDo(print()).andExpect(MockMvcResultMatchers.status().isBadRequest());

        }catch (Exception e){
            logger.error(e.toString());
        }

    }

    @Test
    public void whenSearchClient_withCorrect_minPrice_Parameter_thenOkResponse() throws Exception{

        try{
            mockMvc.perform(post("/searchClient").content("{\"minPrice\":\"5\"}").contentType(MediaType.APPLICATION_JSON_UTF8)).
                    andDo(print()).andExpect(MockMvcResultMatchers.status().isOk());

        }catch (Exception e){
            logger.error(e.toString());
        }

    }

    @Test
    public void whenSearchClient_withCorrect_minPrice_andInCorrect_maxPrice_Parameter_thenBadResponse() throws Exception{

        try{
            mockMvc.perform(post("/searchClient").content("{\"minPrice\":\"5\",\"maxPrice\":\"-1\"}").contentType(MediaType.APPLICATION_JSON_UTF8)).
                    andDo(print()).andExpect(MockMvcResultMatchers.status().isBadRequest());

        }catch (Exception e){
            logger.error(e.toString());
        }

    }

    @Test
    public void whenSearchClient_inCorrect_minReviewRating_Parameter_thenBadResponse() throws Exception{

        try{
            mockMvc.perform(post("/searchClient").content("{\"minReviewRating\":\"-1\"}").contentType(MediaType.APPLICATION_JSON_UTF8)).
                    andDo(print()).andExpect(MockMvcResultMatchers.status().isBadRequest());

        }catch (Exception e){
            logger.error(e.toString());
        }

    }

    @Test
    public void whenSearchClient_inCorrect_maxReviewRating_Parameter_thenBadResponse() throws Exception{

        try{
            mockMvc.perform(post("/searchClient").content("{\"maxReviewRating\":\"6\"}").contentType(MediaType.APPLICATION_JSON_UTF8)).
                    andDo(print()).andExpect(MockMvcResultMatchers.status().isBadRequest());

        }catch (Exception e){
            logger.error(e.toString());
        }

    }

    @Test
    public void whenSearchClient_inCorrect_minReviewCount_Parameter_thenBadResponse() throws Exception{

        try{
            mockMvc.perform(post("/searchClient").content("{\"minReviewCount\":\"-1\"}").contentType(MediaType.APPLICATION_JSON_UTF8)).
                    andDo(print()).andExpect(MockMvcResultMatchers.status().isBadRequest());

        }catch (Exception e){
            logger.error(e.toString());
        }

    }

    @Test
    public void whenSearchClient_inCorrect_maxReviewCount_Parameter_thenBadResponse() throws Exception{

        try{
            mockMvc.perform(post("/searchClient").content("{\"maxReviewCount\":\"-1\"}").contentType(MediaType.APPLICATION_JSON_UTF8)).
                    andDo(print()).andExpect(MockMvcResultMatchers.status().isBadRequest());

        }catch (Exception e){
            logger.error(e.toString());
        }

    }

}
