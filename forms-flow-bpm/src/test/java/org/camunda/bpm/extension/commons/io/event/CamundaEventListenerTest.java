package org.camunda.bpm.extension.commons.io.event;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Test class for CamundaEventListener
 */
@ExtendWith(SpringExtension.class)
public class CamundaEventListenerTest {

	@InjectMocks
	public CamundaEventListener camundaEventListener;

	@Mock
	private SimpMessagingTemplate template;

	/**
	 * Test perform a positive test over onTaskEventListener
	 * This test will validate the template
	 */
	@Test
    public void onTaskEventListener_with_defaultEvents(){
        DelegateTask delegateTask = mock(DelegateTask.class);
        when(delegateTask.getEventName())
                .thenReturn("create");

        ReflectionTestUtils.setField(camundaEventListener, "messageCategory", "TASK_EVENT_DETAILS,TASK_EVENT");
        ReflectionTestUtils.setField(camundaEventListener, "messageEvents", "ALL");
        Map<String, Object> variables = new HashMap<>();
        variables.put("applicationId" , "id1");
        variables.put("formUrl" , "http://localhost:3001");
        variables.put("applicationStatus" , "New");
        when(delegateTask.getVariables())
                .thenReturn(variables);
        camundaEventListener.onTaskEventListener(delegateTask);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(template, times(2)).convertAndSend(anyString(), captor.capture());
        assertEquals("{\"assignee\":null,\"createTime\":null,\"deleteReason\":null,\"description\":null,\"dueDate\":null,\"eventName\":\"create\",\"executionId\":null,\"followUpDate\":null,\"id\":null,\"name\":null,\"owner\":null,\"priority\":0,\"processDefinitionId\":null,\"processInstanceId\":null,\"taskDefinitionKey\":null,\"variables\":{\"applicationStatus\":null,\"formUrl\":null,\"applicationId\":null}}",
                captor.getAllValues().get(0));
        assertEquals("{\"id\":null,\"eventName\":\"create\"}", captor.getAllValues().get(1));
    }

	/**
	 * Test perform a positive test with default message events
	 * This test will validate the template
	 */
    @Test
    public void onTaskEventListener_with_default_messageEvents(){
        DelegateTask delegateTask = mock(DelegateTask.class);
        when(delegateTask.getEventName())
                .thenReturn("create");

        ReflectionTestUtils.setField(camundaEventListener, "messageCategory", "TASK_EVENT_DETAILS,TASK_EVENT");
        ReflectionTestUtils.setField(camundaEventListener, "messageEvents", "DEFAULT");
        Map<String, Object> variables = new HashMap<>();
        variables.put("applicationId" , "id1");
        variables.put("formUrl" , "http://localhost:3001");
        variables.put("applicationStatus" , "New");
        when(delegateTask.getVariables())
                .thenReturn(variables);
        camundaEventListener.onTaskEventListener(delegateTask);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(template, times(2)).convertAndSend(anyString(), captor.capture());
        assertEquals("{\"assignee\":null,\"createTime\":null,\"deleteReason\":null,\"description\":null,\"dueDate\":null,\"eventName\":\"create\",\"executionId\":null,\"followUpDate\":null,\"id\":null,\"name\":null,\"owner\":null,\"priority\":0,\"processDefinitionId\":null,\"processInstanceId\":null,\"taskDefinitionKey\":null,\"variables\":{\"applicationStatus\":null,\"formUrl\":null,\"applicationId\":null}}",
                captor.getAllValues().get(0));
        assertEquals("{\"id\":null,\"eventName\":\"create\"}", captor.getAllValues().get(1));
    }

    /**
	 * Test perform a positive test with custom message events
	 * This test will validate the template
	 */
	@Test
	public void onTaskEventListener_with_custom_messageEvents() {
		DelegateTask delegateTask = mock(DelegateTask.class);
		when(delegateTask.getEventName())
				.thenReturn("create");

		ReflectionTestUtils.setField(camundaEventListener, "messageCategory", "TASK_EVENT_DETAILS,TASK_EVENT");
		ReflectionTestUtils.setField(camundaEventListener, "messageEvents", "create");
		Map<String, Object> variables = new HashMap<>();
		variables.put("applicationId", "id1");
		variables.put("formUrl", "http://localhost:3001");
		variables.put("applicationStatus", "New");
		when(delegateTask.getVariables())
				.thenReturn(variables);
		camundaEventListener.onTaskEventListener(delegateTask);
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(template, times(2)).convertAndSend(anyString(), captor.capture());
		assertEquals(
				"{\"assignee\":null,\"createTime\":null,\"deleteReason\":null,\"description\":null,\"dueDate\":null,\"eventName\":\"create\",\"executionId\":null,\"followUpDate\":null,\"id\":null,\"name\":null,\"owner\":null,\"priority\":0,\"processDefinitionId\":null,\"processInstanceId\":null,\"taskDefinitionKey\":null,\"variables\":{\"applicationStatus\":null,\"formUrl\":null,\"applicationId\":null}}",
				captor.getAllValues().get(0));
		assertEquals("{\"id\":null,\"eventName\":\"create\"}", captor.getAllValues().get(1));
	}
	
	/**
	 * Negetive test case. 
	 * Test perform with no events on onTaskEventListener
	 */
	@Test
	public void onTaskEventListener_with_no_registeredEvents() {
		DelegateTask delegateTask = mock(DelegateTask.class);
		when(delegateTask.getEventName())
				.thenReturn("create");

		camundaEventListener.onTaskEventListener(delegateTask);
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		verify(template, times(0)).convertAndSend(anyString(), captor.capture());
	}
    
}
