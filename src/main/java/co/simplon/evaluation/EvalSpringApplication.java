package co.simplon.evaluation;

import co.simplon.evaluation.dao.CityRepositry;
import co.simplon.evaluation.dao.HotelRepository;
import co.simplon.evaluation.entities.AppRole;
import co.simplon.evaluation.entities.City;
import co.simplon.evaluation.entities.Hotel;
import co.simplon.evaluation.service.AccountService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import java.util.Random;
import java.util.stream.Stream;

@SpringBootApplication
public class EvalSpringApplication implements CommandLineRunner 
{
	@Autowired
	private CityRepositry cityRepositry;
	@Autowired
	private HotelRepository hotelRepository;
	@Autowired
	private RepositoryRestConfiguration restConfiguration;
	
	public static void main(String[] args) 
	{
		SpringApplication.run(EvalSpringApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception 
	{
		restConfiguration.exposeIdsFor(City.class,Hotel.class);
		cityRepositry.save(new City(null,"Toulouse",null));
		cityRepositry.save(new City(null,"Paris",null));
		cityRepositry.save(new City(null,"Besançon",null));
		Random rnd = new Random();
		
		cityRepositry.findAll().forEach(h -> 
		{
			for(int i=0;i<5;i++) 
			{
				Hotel hotel = new Hotel();
				hotel.setName(RandomString.make(8));
				hotel.setSelected(rnd.nextBoolean());
				hotel.setPhone("06"+Integer.toString(rnd.nextInt(9-0))+Integer.toString(rnd.nextInt(9-0))+Integer.toString(rnd.nextInt(9-0))+Integer.toString(rnd.nextInt(9-0))+Integer.toString(rnd.nextInt(9-0))+Integer.toString(rnd.nextInt(9-0))+Integer.toString(rnd.nextInt(9-0))+Integer.toString(rnd.nextInt(9-0)));
				hotel.setAdress(RandomString.make(10));
				hotel.setNbStars(rnd.nextInt(4 - 0+1)+0);
				hotel.setNbRoomDispo(rnd.nextInt(100-0));
				hotel.setPhotoName("wordpress-cities-640x400 .png");
				hotel.setCity(h);
				hotelRepository.save(hotel);
			}
		});

	}
	
	@Bean
	CommandLineRunner start(AccountService accountService)
	{
		return args -> 
		{
			accountService.save(new AppRole(null,"USER"));
			accountService.save(new AppRole(null,"ADMIN"));
			
			Stream.of("user1","user2","user3","admin").forEach(un->
			{
				accountService.saveUser(un,"1234","1234");
			});
			
			accountService.addRoleToUser("admin","ADMIN");
		};
	}
}
