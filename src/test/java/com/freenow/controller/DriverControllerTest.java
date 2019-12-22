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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DriverControllerTest
{
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    //    @MockBean
    //    private DriverService driverService;
    //
    //    @MockBean
    //    private CarSelectionService carSelectionService;


    @Test
    public void getDriversTest() throws Exception
    {
        ResultActions resultActions = this.mockMvc.perform(get("/v1/drivers?onlineStatus=ONLINE"))
            .andDo(print())
            .andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<DriverDO> driverDOS = Arrays.asList(objectMapper.readValue(contentAsString, DriverDO[].class));
        assertThat(driverDOS.size()).isEqualTo(4);
    }


    @Test
    public void filterDriversTest() throws Exception
    {
        ResultActions resultActions1 = this.mockMvc.perform(put("/v1/drivers/selectCar/8?licensePlate=ABC123")).andDo(print()).andExpect(status().isOk());
        ResultActions resultActions = this.mockMvc.perform(get("/v1/drivers/filter?onlineStatus=ONLINE&licensePlate=ABC123")).andDo(print()).andExpect(status().isOk());

        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<DriverDO> driverDOS = Arrays.asList(objectMapper.readValue(contentAsString, DriverDO[].class));
        assertThat(driverDOS.size()).isEqualTo(1);

    }


    @Test
    public void selectCar_DriverNotFound() throws Exception
    {
        ResultActions resultActions = this.mockMvc.perform(get("/v1/drivers?onlineStatus=ONLINE"))
            .andDo(print())
            .andExpect(status().isOk());
        MvcResult mvcResult = resultActions.andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        List<DriverDO> driverDOS = Arrays.asList(objectMapper.readValue(contentAsString, DriverDO[].class));
        assertThat(driverDOS.size()).isEqualTo(4);
    }


    @Test
    public void selectCar_CarNotFound()
    {
    }


    @Test
    public void deselectCar()
    {
    }


    @Test
    public void findDrivers()
    {
    }
}