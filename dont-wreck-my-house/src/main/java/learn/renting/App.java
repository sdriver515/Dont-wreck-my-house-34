package learn.renting;

import learn.renting.ui.Controller;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan
@PropertySource("classpath:data.properties")
public class App {
    public static void main(String[] args) {

        configureWithAnnotationsAndRun();

    }//main

    private static void configureWithAnnotationsAndRun(){
        ApplicationContext container = new AnnotationConfigApplicationContext(App.class);
        Controller controller = container.getBean(Controller.class);
//        controller.run();
    }//configureWithAnnotationsAndRun

}//end
