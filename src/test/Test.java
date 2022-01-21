package test;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import metier.beans.SmartPhone;
import metier.beans.User;
import metier.smartphoneejb.SmartPhoneRemote;
import metier.userejb.UserRemote;

public class Test {

	public static <T> T lookUpUserRemoteStateless(String jndi) throws NamingException {
		final Hashtable<Object, Object> jndiProperties = new Hashtable<Object, Object>();
		jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		final Context context = new InitialContext(jndiProperties);
		return (T) context.lookup(jndi);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			UserRemote userRemote = lookUpUserRemoteStateless("ejb:/EjbDemo/UserSession!metier.userejb.UserRemote");
			System.out.println("Found user Remote !!!!!");
			SmartPhoneRemote smartPhoneRemote = lookUpUserRemoteStateless("ejb:/EjbDemo/SmartPhoneSession!metier.smartphoneejb.SmartPhoneRemote");
			System.out.println("Found Smart Phone Remote !!!");
			userRemote.save(new User("Belghali","Mehdi","0678148298","medmehdibelghali@gmail.com",new Date()));
			userRemote.save(new User("Kecha","Hamza","0674221586","kechaHamza@gmail.com",new Date()));
			System.out.println("Successfully created 2 users and here they are : ");
			List<User> allUsers = userRemote.findAll();
			System.out.println("FETCHED ALL USERS");
			for (User u : allUsers) {
				System.out.println(u);
			}
			System.out.println("Fetching a user by mail : ");
			User uu = userRemote.findByEmail("medmehdibelghali@gmail.com");
			if (uu != null) {
				System.out.println("We found a user by mail : " + uu);
			}
			smartPhoneRemote.save(new SmartPhone(null,"ref-1", "Hwawei Y-9", "Hwawei",null,null));
			smartPhoneRemote.save(new SmartPhone(null,"ref-2", "SAMSUNG GALAXY 10", "Samsung",null,null));
			System.out.println("Successfully created 2 smartPhones for the no user");
			System.out.println("Let's try to update a user ---------------------------");
			userRemote.update(new User("Belghali","Mohamed Mehdi","0678148298","medmehdibelghali@gmail.com",new Date()));
			System.out.println("AFTER UPDATE :    ");
			for (User u : userRemote.findAll()) {
				System.out.println(u);
			}
			System.out.println("Let's create a smartPhone with the user who has medmehdibelghali@gmail.com as an email ");
			smartPhoneRemote.save(new SmartPhone(null,"ref-3", "Iphone X", "iPhone", uu, null));
			System.out.println("Trying to fetch smart phone with ref : ref-3");
			SmartPhone ss = smartPhoneRemote.findByRef("ref-3");
			System.out.println(" This is the smart Phone : " + ss);
			System.out.println("Lets update this smart phone from Iphone X to iPhone XI");
			smartPhoneRemote.update(new SmartPhone(null,"ref-3", "iPhone XI", "iPhone", uu,null));
			System.out.println("Updated successfully");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
