package com.atfarm.challenge.service;

import com.atfarm.challenge.AtfarmApp;
import com.atfarm.challenge.config.WebConfigurer;
import com.atfarm.challenge.service.dto.FieldConditionDTO;
import com.atfarm.challenge.service.impl.InMemoryFieldServiceImpl;
import io.github.jhipster.config.JHipsterProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.mock.web.MockServletContext;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.Servlet;
import javax.servlet.ServletRegistration;
import java.sql.Date;
import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = AtfarmApp.class)
public class FieldServiceTest {

    private WebConfigurer webConfigurer;

    private MockServletContext servletContext;

    private MockEnvironment env;

    private JHipsterProperties props;

    @BeforeEach
    public void setup() {
        servletContext = spy(new MockServletContext());
        doReturn(mock(FilterRegistration.Dynamic.class))
            .when(servletContext).addFilter(anyString(), any(Filter.class));
        doReturn(mock(ServletRegistration.Dynamic.class))
            .when(servletContext).addServlet(anyString(), any(Servlet.class));

        env = new MockEnvironment();
        props = new JHipsterProperties();

        webConfigurer = new WebConfigurer(env, props);
    }


    @Test
    public void testIsAbleToAddTransactionsAndCalculateStatistics() {
        InMemoryFieldServiceImpl fieldService = new InMemoryFieldServiceImpl();

        assertThat(fieldService.getStatistics().getCount()).isEqualTo(0);
        assertThat(fieldService.getStatistics().getAvg()).isEqualTo( 0);

        double amount = 456.78;
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        FieldConditionDTO fieldConditionDTO = new FieldConditionDTO(amount, date);

        fieldService.addFieldCondition(fieldConditionDTO);
        fieldService.addFieldCondition(fieldConditionDTO);

        assertThat(fieldService.getStatistics().getCount()).isEqualTo(2);
        assertThat(fieldService.getStatistics().getSum()).isEqualTo(amount * 2);
        assertThat(fieldService.getStatistics().getAvg()).isEqualTo(amount);

    }

    @Test
    public void testNotBeAbleToAddExpiredTransactions() {
        InMemoryFieldServiceImpl fieldService = new InMemoryFieldServiceImpl();

        double amount = 123456.78;
        long timestamp = System.currentTimeMillis() - 70000;

        Date date = new Date(Calendar.getInstance().getTime().getTime() - 32 * 24 * 60 * 60 * 1000l);

        FieldConditionDTO transaction = new FieldConditionDTO(amount, date);
        fieldService.addFieldCondition(transaction);
        assertThat(fieldService.getStatistics().getCount()).isEqualTo(0);

    }

    @Test
    public void testBeAbleToGetStatisticsOnlyForTransactionsOfLastMonth() {
        InMemoryFieldServiceImpl fieldService = new InMemoryFieldServiceImpl();

        Date date = new Date(Calendar.getInstance().getTime().getTime());

        Date date2 = new Date(Calendar.getInstance().getTime().getTime() - (30 * 24 * 60 * 60 * 1000l ) + 9000);

        fieldService.addFieldCondition(new FieldConditionDTO(456.78, date));

        fieldService.addFieldCondition(new FieldConditionDTO(123.45, date2));

        assertThat(fieldService.getStatistics().getCount()).isEqualTo(2);

        try {
            Thread.sleep(11000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertThat(fieldService.getStatistics().getCount()).isEqualTo(1);

    }
}
