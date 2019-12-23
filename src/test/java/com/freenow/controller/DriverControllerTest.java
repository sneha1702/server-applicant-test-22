package com.freenow.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freenow.domainobject.DriverDO;
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
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DriverControllerTest
{
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void getDriversTest() throws Exception
    {
        ResultActions resultActions = this.mockMvc.perform(get("/v1/drivers?onlineStatus=ONLINE"))
            .andDo(print())
            .andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<DriverDO> driverDOS = Arrays.asList(objectMapper.readValue(contentAsString, DriverDO[].class));
        assertThat(driverDOS.size()).isEqualTo(5);
    }


    @Test
    public void filterDriversByDriverAttributesTest() throws Exception
    {
        ResultActions resultActions = this.mockMvc.perform(get("/v1/drivers/filter?onlineStatus=ONLINE&username=driver05")).andDo(print()).andExpect(status().isOk());

        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<DriverDO> driverDOS = Arrays.asList(objectMapper.readValue(contentAsString, DriverDO[].class));
        assertThat(driverDOS.size()).isEqualTo(1);
        assertThat(driverDOS.get(0).getUsername()).contains("driver05");
    }


    @Test
    public void filterDriversByCarAttributesTest() throws Exception
    {
        this.mockMvc.perform(put("/v1/drivers/selectCar/7?licensePlate=XYZ123")).andDo(print()).andExpect(status().isOk());
        ResultActions resultActions =
            this.mockMvc.perform(get("/v1/drivers/filter?onlineStatus=ONLINE&licensePlate=XYZ123&engineType=GAS&convertible=true&rating=10"))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<DriverDO> driverDOS = Arrays.asList(objectMapper.readValue(contentAsString, DriverDO[].class));
        assertThat(driverDOS.size()).isEqualTo(1);
    }


    @Test
    public void selectCar_DriverNotFound() throws Exception
    {
        ResultActions resultActions1 = this.mockMvc.perform(put("/v1/drivers/selectCar/10?licensePlate=ABC123"))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(status().reason(containsString("Driver not found!")));
    }


    @Test
    public void selectCar_CarNotFound() throws Exception
    {
        ResultActions resultActions1 = this.mockMvc.perform(put("/v1/drivers/selectCar/8?licensePlate=INVALID123"))
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(status().reason(containsString("Car not found!")));
    }


    @Test
    public void deselectCar() throws Exception
    {
        this.mockMvc.perform(put("/v1/drivers/selectCar/4?licensePlate=XYZ456")).andDo(print()).andExpect(status().isOk());
        this.mockMvc.perform(put("/v1/drivers/deselectCar/4")).andDo(print()).andExpect(status().isOk());
        this.mockMvc.perform(put("/v1/drivers/selectCar/4?licensePlate=ABC123")).andDo(print()).andExpect(status().isOk());

    }


    @Test
    public void selectAlreadySelectedCar() throws Exception
    {
        this.mockMvc.perform(put("/v1/drivers/selectCar/8?licensePlate=ABC123")).andDo(print()).andExpect(status().isOk());

        this.mockMvc.perform(put("/v1/drivers/selectCar/5?licensePlate=ABC123")).andDo(print()).andExpect(status().is(417));
    }


    @Test
    public void offlineDriverCannotSelectCar() throws Exception
    {
        this.mockMvc.perform(put("/v1/drivers/selectCar/3?licensePlate=XYZ123")).andDo(print()).andExpect(status().isBadRequest());
    }
}