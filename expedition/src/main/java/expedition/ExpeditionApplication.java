package expedition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.pivotal.springcloud.ssl.CloudFoundryCertificateTruster;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class ExpeditionApplication {

    private Log log = LogFactory.getLog(ExpeditionApplication.class);

    public static void main(String[] args) {
        CloudFoundryCertificateTruster.trustApiCertificate();
        SpringApplication.run(ExpeditionApplication.class, args);
    }

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="salutation", defaultValue="Hello") String salutation, @RequestParam(value="name", defaultValue="camel boy") String name) {
        log.info(String.format("Now saying \"%s\" to %s", salutation, name));
        return new Greeting(salutation, name);
    }

    private static class Greeting {

        private static final String template = "%s, %s!";

        private String message;

        public Greeting(String salutation, String name) {
            this.message = String.format(template, salutation, name);
        }

        public String getMessage() {
            return this.message;
        }

    }

}
