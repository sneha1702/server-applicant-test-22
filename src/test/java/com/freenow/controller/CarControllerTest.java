package com.freenow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freenow.domainobject.CarDO;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CarControllerTest
{
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void getCarsTest() throws Exception
    {
        ResultActions resultActions = this.mockMvc.perform(get("/v1/cars"))
            .andDo(print())
            .andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<CarDO> carDOS = Arrays.asList(objectMapper.readValue(contentAsString, CarDO[].class));
        assertThat(carDOS.size()).isEqualTo(3);
    }


    @Test
    public void getValidCarByIdTest() throws Exception
    {
        ResultActions resultActions = this.mockMvc.perform(get("/v1/cars/3"))
            .andDo(print())
            .andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<CarDO> carDOS = Arrays.asList(objectMapper.readValue(contentAsString, CarDO[].class));
        assertThat(carDOS.size()).isEqualTo(1);
        assertThat(carDOS.get(0).getLicensePlate()).containsIgnoringCase("XYZ456");

    }


    @Test
    public void getUnexistingCarById_CarNotFoundExceptionTest() throws Exception
    {
        this.mockMvc.perform(get("/v1/cars/20"))
            .andDo(print())
            .andExpect(status().isNotFound());
    }
}