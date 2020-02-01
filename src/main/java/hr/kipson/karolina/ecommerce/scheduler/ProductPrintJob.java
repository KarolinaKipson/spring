package hr.kipson.karolina.ecommerce.scheduler;

import hr.kipson.karolina.ecommerce.model.Product;
import hr.kipson.karolina.ecommerce.repository.ProductRepository;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class ProductPrintJob extends QuartzJobBean {

    private Logger log = LoggerFactory.getLogger(ProductPrintJob.class);

    private final ProductRepository productRepository;

    public ProductPrintJob(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Iterable<Product> products = productRepository.findAll();

        if(products.iterator().hasNext()) {
            log.info("These are the products currently entered in the app");
            products.forEach(it ->
                    log.info(it.getName())
            );
        } else {
            log.info("These are currently no products in the app");
        }
    }
}
