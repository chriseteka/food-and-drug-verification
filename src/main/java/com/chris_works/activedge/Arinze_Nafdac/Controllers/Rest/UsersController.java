package com.chris_works.activedge.Arinze_Nafdac.Controllers.Rest;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chris_works.activedge.Arinze_Nafdac.Entities.PasswordResetEntity;
import com.chris_works.activedge.Arinze_Nafdac.Entities.Products;
import com.chris_works.activedge.Arinze_Nafdac.Entities.Users;
import com.chris_works.activedge.Arinze_Nafdac.Services.GeneralServices.GeneralServices;
import com.chris_works.activedge.Arinze_Nafdac.Services.UserServices.UserServices;
import com.chris_works.activedge.Arinze_Nafdac.functions.AddProducts;
import com.chris_works.activedge.Arinze_Nafdac.functions.LoginUsers;
import com.chris_works.activedge.Arinze_Nafdac.functions.PasswordResetter;
import com.chris_works.activedge.Arinze_Nafdac.functions.RegisterUsers;
import com.chris_works.activedge.Arinze_Nafdac.functions.RemoveProduct;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/user")
@Api(value="Foods_And_Drugs_Verification App", description="Operations pertaining to users in Foods_And_Drugs_Verification App")
public class UsersController {
	
	@Autowired
	private RegisterUsers registerUser;
	
	@Autowired
	private AddProducts addproduct;
	
	@Autowired
	private UserServices userService;
	
	@Autowired
	private GeneralServices generalService;
	
	@Autowired
	private LoginUsers loginUsers;
	
	@Autowired
	private PasswordResetter passwordReset;
	
	@Autowired
	private RemoveProduct removeProduct;
	
	

//	################################################################################################################
	
	//THE FOLLOWING ARE JUST FOR TESTS, I WILL PILL THEM OFF
//	@CrossOrigin
//	@GetMapping(path = "/showusers")
//	@ApiOperation(value = "View a list of available users", response = Iterable.class)
//	public List<Users> showAllUsers() 
//	{
//		List<Users> showAllRegisteredUsers = service.showAllRegisteredUsers();
//		return showAllRegisteredUsers;
//	}
	
//	@CrossOrigin
//	@GetMapping(path = "/getuser/{id}")
//	@ApiOperation(value = "View a user by his id {email}", response = Users.class)
//	public Users showUser(@PathVariable("id") long id) 
//	{
//		Users showAllRegisteredUsers = service.UserByUserId(id);
//		return showAllRegisteredUsers;
//	}
	
	//THE PILL OFF MUST END HERE ONCE AM DONE WITH IT
	
//	################################################################################################################
	
	
	//SIGN IN AND SIGN OUT FUNCTION HAS TO BE RE FIXED FOR PROPER STATUS CODE REPORTS, FOR NOW, GOOD
	@CrossOrigin
	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "process and permits user login using {email} and {password}", response = ResponseEntity.class)
	public ResponseEntity<?> userLogin(@RequestBody Users user) 
	{
		ResponseEntity<?> userSignInResponse = loginUsers.userSignIn(user);
		return userSignInResponse;
	}
	
	
	//SIGNUP FUNCTION NEEDS TO BE FIXED TO RETURN APPROPRIATE STATUS CODE(FOR NOW ONLY 200 AND 400 RETURNS)
	@CrossOrigin
	@RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "processes and allows new user signup", response = ResponseEntity.class)
	public ResponseEntity<?> userSignup(@RequestBody Users user) 
	{
		ResponseEntity<?> signUpResponse = registerUser.signUp(user);
		return signUpResponse;
	}
	
	
	//THIS AIDS A REGISTERED USER TO ADD A PRODUCT MEANT TO BE VERIFIED
	@CrossOrigin
	@RequestMapping(value = "/{id}/addproduct", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Allows a registered user to add a product", response = ResponseEntity.class)
	public ResponseEntity<?> registerProductWithUid(@RequestBody Products product,
 													@PathVariable("id") long id,
													@PathVariable("id") Set<Users> uid)
	{
		ResponseEntity<?> addProductResponse = addproduct.Product(product, id, uid);
		return addProductResponse;
	}
	
	//THIS AIDS A REGISTERED USER TO REMOVE A PRODUCT.
	@CrossOrigin
	@RequestMapping(value = "/remove/product", method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Allows a registered user to remove a product he or she added", response = ResponseEntity.class)
	public ResponseEntity<?> removeProductWithUid(@RequestBody Products product)
	{
		ResponseEntity<?> deletedProduct = removeProduct.deleteProduct(product);
		return deletedProduct;
	}
	
	//THIS AIDS IN THE SEARCH OF ALL REGISTERED PRODUCTS AND RETRIEVES THEM BY THE PRODUCT SEARCH ID.
	@CrossOrigin
	@RequestMapping(path = "/search/product", method=RequestMethod.POST)
	@ApiOperation(value = "View a product using its {productsearchcode}", response = ResponseEntity.class)
	public ResponseEntity<?> searchProducts(@RequestBody Products product)
	{
		String productId = product.getProductsearchcode();
		ResponseEntity<?> productRetrieved = generalService.searchProduct(productId);
		return productRetrieved;
	}
	
	
	//THIS HELPS TO RESET A PASSWORD BY GETTING THE USERS MAIL
	@CrossOrigin
	@RequestMapping(path = "/passwordreset", method=RequestMethod.POST)
	@ApiOperation(value = "Allows a user to send a password reset request using his id {email}", response = ResponseEntity.class)
	public ResponseEntity<?> resetPassword(@RequestBody PasswordResetEntity resetEntity) 
	{
		ResponseEntity<?> passwordResetRequest = passwordReset.passwordResetRequest(resetEntity);
		return passwordResetRequest;
	}
	
	
	//THIS AIDS IN THE RESET OF A FORGOTTEN PASSWORD, BY THE USE OF A RESET CODE SENT TO ONES EMAIL
	@CrossOrigin
	@RequestMapping(path = "/passwordreset/proceed", method=RequestMethod.POST)
	@ApiOperation(value = "Proceeds password reset request using the {resetcode}", response = ResponseEntity.class)
	public ResponseEntity<?> proceedPasswordReset(@RequestBody PasswordResetEntity passwordResetEntity) 
	{
		String resetCode = passwordResetEntity.getResetcode();
		String passwordResetterResponse = passwordReset.passwordResetter(resetCode);
		if(!passwordResetterResponse.equals(null) && !passwordResetterResponse.contains("WRONG RESET CODE ENTERED"))
		{
			return new ResponseEntity<String>(passwordResetterResponse, HttpStatus.OK);
		}
		else
		{
			return new ResponseEntity<String>(passwordResetterResponse, HttpStatus.NOT_MODIFIED);
		}
	}

	
	//THIS AIDS IN THE CHANGE OF A PASSWORD
	@CrossOrigin
	@RequestMapping(path = "/changepassword", method=RequestMethod.POST)
	@ApiOperation(value = "Proceeses password changing by a user who is logged in", response = ResponseEntity.class)
	public ResponseEntity<?> changePassword(@RequestBody Users user)
	{
		String errorMessage = "PASSWORD CHANGE WAS UNSUCCESSFUL, CONFIRM YOUR INPUTS AND TRY AGAIN";
		
		String email = user.getEmail();
		String oldPassword = user.getOldpassword();
		String newPassword = user.getNewpassword();
		Users userPasswordChanged = generalService.changeOfPassword(email, oldPassword, newPassword);
		if(userPasswordChanged != null)
		{
			return new ResponseEntity<Users>(userPasswordChanged, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>(errorMessage, HttpStatus.NOT_MODIFIED);
		}
	}
	
	//THIS AIDS A USER TO VIEW ALL PRODUCTS HE OR SHE HAS ADDED
	@CrossOrigin
	@RequestMapping(path = "/{id}/products", method = RequestMethod.POST)
	@ApiOperation(value = "fetches all product added by a particular user, using his identity, this implies that a user must be logged in to do this", response = ResponseEntity.class)
	public ResponseEntity<?> viewMyProducts(@PathVariable("id") Set<Users> userid){
		if(!userid.isEmpty()) {
			List<Products> myProducts = userService.viewMyProducts(userid);
			if(!myProducts.isEmpty()) {
				return new ResponseEntity<List<Products>>(myProducts, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("NO PRODUCTS HAS BEEN REGISTERED TO YOUR NAME", HttpStatus.NO_CONTENT);
			}
		}
		else {
			return new ResponseEntity<String>("WE COULD NOT CONFIRM YOUR IDENTITY TO PROCEED, TRY AGAIN WITH A DIFFERENT ID", HttpStatus.UNAUTHORIZED);
		}
	}
	
	//THIS AIDS A USER TO EDIT HIS PROFILE, ALTHOUGH NOT ALL PROFILE IS EDITABLE
	@CrossOrigin
	@RequestMapping(path = "/{id}/editprofile", method = RequestMethod.POST)
	@ApiOperation(value = "This allows a logged in user to edit his profile and change some fields", response = ResponseEntity.class)
	public ResponseEntity<?> editUserProfile(@RequestBody Users user, @PathVariable("id") long userid){
		ResponseEntity<?> editedUserProfile = userService.editProfile(user, userid);
		return editedUserProfile;
	}
	
	//THIS ALLOWS A USER TO EDIT A PRODUCT ONLY HE ADDED
	@CrossOrigin
	@RequestMapping(path = "/{id}/editproduct", method = RequestMethod.POST)
	@ApiOperation(value = "This allow a user to edit some details about his product", response = ResponseEntity.class)
	public ResponseEntity<?> editProduct(@RequestBody Products product, @PathVariable("id") Set<Users> userid){
		if(product != null) {
			if(!userid.isEmpty()) {
				Products editedProduct = userService.editProduct(product, userid);
				if(editedProduct != null) {
					return new ResponseEntity<Products>(editedProduct, HttpStatus.OK);
				}
				else {
					return new ResponseEntity<String>("YOUR PRODUCT WAS UNABLE TO BE EDITED, TRY AGAIN LATER", HttpStatus.NOT_MODIFIED);
				}
			}else {
				return new ResponseEntity<String>("WE CANNOT CONFIRM YOUR IDENTITY, PLEASE TRY AGAIN WITH A VALID USER ID.", HttpStatus.NOT_FOUND);
			}
		}else {
			return new ResponseEntity<String>("YOUR FIELDS ARE EMPTY OR YOU MAY HAVE PASSED AN UNKNOWN VARIABLE", HttpStatus.NOT_ACCEPTABLE);
		}
	}

}
