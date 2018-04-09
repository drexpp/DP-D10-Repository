
package controllers;

import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Newspaper;

import services.NewspaperService;


@Controller
@RequestMapping("/newspaper")
public class NewspaperController extends AbstractController {

	// Services

	@Autowired
	private NewspaperService	newspaperService;


	// Constructors

	public NewspaperController() {
		super();
	}

	//Display
		@RequestMapping(value = "/display", method = RequestMethod.GET)
		public ModelAndView display(@RequestParam final int newspaperId) {
			final ModelAndView result;
			Newspaper newspaper;
			final String uri = "";

			newspaper = this.newspaperService.findOne(newspaperId);

			result = new ModelAndView("newspaper/display");
			result.addObject("newspaper", newspaper);
			result.addObject("uri", uri);
			result.addObject("principal", null);
			return result;

	}
		
	// Listing
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final String filter) {
		ModelAndView result;
		Collection<Newspaper> newspapers;
		final String uri = "";
		newspapers = this.newspaperService.findByFilter(filter);
		result = new ModelAndView("newspaper/list");
		result.addObject("newspapers", newspapers);
		result.addObject("uri", uri);
		return result;
	}	


}
