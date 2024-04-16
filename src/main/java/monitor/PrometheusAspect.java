package monitor;

import io.prometheus.client.Summary;
import io.prometheus.client.exporter.HTTPServer;
import java.io.IOException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PrometheusAspect {

  static {
    try {
      new HTTPServer(9090);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  Summary titleTimer = Summary.build()
      .namespace("java")
      .name("title_search_timer")
      .help("Measures the time it takes to run a title search")
      .register();


  @Pointcut("execution(* *.displayWelcome(..))")
  public void presenterStart() {
  }

  @Pointcut("execution(* *.titleSearch(String))")
  public void titleSearch() {
  }

  @Pointcut("execution(* *.authorSearch(String))")
  public void authorSearch() {
  }

  @Pointcut("execution(* *.topicSearch(String))")
  public void topicSearch() {
  }

  //TODO: Create advices to start the Prometheus exporter and the three timers
  @Around("titleSearch()")
  public Object aroundTitleSearch(ProceedingJoinPoint joinPoint) throws Throwable {
    Summary.Timer timer = titleTimer.startTimer();
    try {
      return joinPoint.proceed();
    } finally {
      timer.observeDuration();
    }
  }

  Summary authorTimer = Summary.build()
          .namespace("java")
          .name("author_search_timer")
          .help("Measures the time it takes to run an author search")
          .register();

  Summary topicTimer = Summary.build()
          .namespace("java")
          .name("topic_search_timer")
          .help("Measures the time it takes to run a topic search")
          .register();

  Summary welcomeTimer = Summary.build()
          .namespace("java")
          .name("welcome_display_timer")
          .help("Measures the time it takes to display the welcome message")
          .register();


  @Around("authorSearch()")
  public Object aroundAuthorSearch(ProceedingJoinPoint joinPoint) throws Throwable {
    Summary.Timer timer = authorTimer.startTimer();
    try {
      return joinPoint.proceed();
    } finally {
      timer.observeDuration();
    }
  }

  @Around("topicSearch()")
  public Object aroundTopicSearch(ProceedingJoinPoint joinPoint) throws Throwable {
    Summary.Timer timer = topicTimer.startTimer();
    try {
      return joinPoint.proceed();
    } finally {
      timer.observeDuration();
    }
  }

  @Around("presenterStart()")
  public Object aroundDisplayWelcome(ProceedingJoinPoint joinPoint) throws Throwable {
    Summary.Timer timer = welcomeTimer.startTimer();
    try {
      return joinPoint.proceed();
    } finally {
      timer.observeDuration();
    }
  }

}
