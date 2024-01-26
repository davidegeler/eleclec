package com.lb3.webshop.services;

import com.lb3.webshop.models.User;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.engine.ExternalTaskService;
import org.camunda.bpm.engine.externaltask.LockedExternalTask;
import org.camunda.bpm.engine.impl.ExternalTaskServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LoginService {

    @Autowired
    UserService userService;


    public boolean handleLogin(String email, String password) {

        ExternalTaskClient client = ExternalTaskClient.create()
                .baseUrl("http://localhost:8080/engine-rest")
                .workerId("meinExternalWorker1") // Setzen der externalWorkerId
                .build();

        ExternalTaskService externalTaskService = new ExternalTaskServiceImpl();
        List<LockedExternalTask> tasks = externalTaskService.fetchAndLock(10, "meinExternalWorker1")
                .topic("Login", 60L * 1000L)
                .execute();

        for (LockedExternalTask task : tasks) {
            try {
                String Login = task.getTopicName();

                if (!this.userService.findUserByEmailAndPassword(email, password).isEmpty()) {
                    externalTaskService.complete(task.getId(), "true");
                    return true;
                } else {
                    // if the work was not successful, mark it as failed
                    externalTaskService.handleFailure(
                            task.getId(),
                            "meinExternalWorker1",
                            "Access to DB was not possible",
                            1, 10L * 60L * 1000L);
                    return false;
                }
            } catch (Exception e) {
                System.out.println("Error occured");
                System.out.println(e);
                return false;
            }
        }
        return false;
    }
}
