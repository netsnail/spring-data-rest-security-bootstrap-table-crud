package example.springdata.rest.security;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {
	
	@PersistenceContext EntityManager em;
	
	@RequestMapping("/items/groupby/category")
	@ResponseBody
	public List<Object[]> getGroupbyCategory() {
		return em.createNativeQuery("select category, count(1) from peop_product group by category").getResultList();
	}
	
	@RequestMapping("/items/groupby/province")
	@ResponseBody
	public List<Object[]> getGroupbyProvince() {
		return em.createNativeQuery("select province, count(1) from peop_product group by province").getResultList();
	}
}
