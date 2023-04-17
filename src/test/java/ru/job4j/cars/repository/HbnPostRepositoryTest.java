package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;

class HbnPostRepositoryTest {
    private final SessionFactory sf = new MetadataSources(
            new StandardServiceRegistryBuilder().configure().build()
    ).buildMetadata().buildSessionFactory();
    private final CrudRepository crudRepository = new CrudRepository(sf);
    private final HbnPostRepository hbnPostRepository = new HbnPostRepository(crudRepository);
    private final HbnCarRepository hbnCarRepository = new HbnCarRepository(crudRepository);
    private final HbnEngineRepository hbnEngineRepository = new HbnEngineRepository(crudRepository);
    private final HbnFileRepository hbnFileRepository = new HbnFileRepository(crudRepository);
    private final HbnBodyRepository hbnBodyRepository = new HbnBodyRepository(crudRepository);
    private final HbnTransmissionRepository hbnTransmissionRepository =
            new HbnTransmissionRepository(crudRepository);

    @AfterEach
    public void wipeTable() {
        var session = sf.openSession();
        session.beginTransaction();
        session.createQuery("delete from Post").executeUpdate();
        session.getTransaction();
        session.close();
    }

    @Test
    public void whenAddNewPostThenRepoHasSamePost() {
        var engine = new Engine();
        engine.setName("engine");
        hbnEngineRepository.create(engine);
        var transmission = new Transmission();
        transmission.setName("auto");
        hbnTransmissionRepository.create(transmission);
        var body = new Body();
        body.setName("sedan");
        hbnBodyRepository.create(body);
        var car = new Car();
        car.setName("car");
        car.setEngine(engine);
        car.setBody(body);
        car.setTransmission(transmission);
        hbnCarRepository.create(car);
        var file = new File();
        file.setName("name");
        file.setPath("some/path");
        hbnFileRepository.create(file);
        var post = new Post();
        post.setDescription("some desc");
        post.setCreated(LocalDateTime.now());
        post.setCar(car);
        post.setFile(file);
        hbnPostRepository.create(post);
        assertThat(hbnPostRepository.findById(post.getId()).get()).isEqualTo(post);
    }

    @Test
    public void whenReplace() {
        var engine = new Engine();
        engine.setName("engine");
        hbnEngineRepository.create(engine);
        var transmission = new Transmission();
        transmission.setName("auto");
        hbnTransmissionRepository.create(transmission);
        var body = new Body();
        body.setName("sedan");
        hbnBodyRepository.create(body);
        var car = new Car();
        car.setName("car");
        car.setEngine(engine);
        car.setBody(body);
        car.setTransmission(transmission);
        hbnCarRepository.create(car);
        var file = new File();
        file.setName("name");
        file.setPath("some/path");
        hbnFileRepository.create(file);
        var post = new Post();
        post.setDescription("some desc");
        post.setCreated(LocalDateTime.now());
        post.setCar(car);
        post.setFile(file);
        hbnPostRepository.create(post);
        post.setDescription("some new desc");
        hbnPostRepository.update(post);
        var rsl = hbnPostRepository.findById(post.getId()).get().getDescription();
        assertThat(rsl).isEqualTo(post.getDescription());
    }

    @Test
    public void whenDelete() {
        var engine = new Engine();
        engine.setName("engine");
        hbnEngineRepository.create(engine);
        var transmission = new Transmission();
        transmission.setName("auto");
        hbnTransmissionRepository.create(transmission);
        var body = new Body();
        body.setName("sedan");
        hbnBodyRepository.create(body);
        var car = new Car();
        car.setName("car");
        car.setEngine(engine);
        car.setBody(body);
        car.setTransmission(transmission);
        hbnCarRepository.create(car);
        var file = new File();
        file.setName("name");
        file.setPath("some/path");
        hbnFileRepository.create(file);
        var post = new Post();
        post.setDescription("some desc");
        post.setCreated(LocalDateTime.now());
        post.setCar(car);
        post.setFile(file);
        hbnPostRepository.create(post);
        hbnPostRepository.delete(post.getId());
        assertThat(hbnPostRepository.findById(post.getId())).isEqualTo(empty());
    }

    @Test
    public void whenFindAll() {
        var engine = new Engine();
        engine.setName("engine");
        hbnEngineRepository.create(engine);
        var transmission = new Transmission();
        transmission.setName("auto");
        hbnTransmissionRepository.create(transmission);
        var body = new Body();
        body.setName("sedan");
        hbnBodyRepository.create(body);
        var car = new Car();
        car.setName("car");
        car.setEngine(engine);
        car.setBody(body);
        car.setTransmission(transmission);
        hbnCarRepository.create(car);
        var file = new File();
        file.setName("name");
        file.setPath("some/path");
        hbnFileRepository.create(file);
        var post1 = new Post();
        var post2 = new Post();
        var post3 = new Post();
        post1.setDescription("some desc1");
        post1.setCreated(LocalDateTime.now());
        post1.setCar(car);
        post1.setFile(file);
        post2.setDescription("some desc2");
        post2.setCreated(LocalDateTime.now());
        post2.setCar(car);
        post2.setFile(file);
        post3.setDescription("some desc3");
        post3.setCreated(LocalDateTime.now());
        post3.setCar(car);
        post3.setFile(file);
        hbnPostRepository.create(post1);
        hbnPostRepository.create(post2);
        hbnPostRepository.create(post3);
        assertThat(hbnPostRepository.findAllOrderById()).isEqualTo(List.of(post1, post2, post3));
    }

    @Test
    public void whenFindAllForTheLastDay() {
        var engine = new Engine();
        engine.setName("engine");
        hbnEngineRepository.create(engine);
        var transmission = new Transmission();
        transmission.setName("auto");
        hbnTransmissionRepository.create(transmission);
        var body = new Body();
        body.setName("sedan");
        hbnBodyRepository.create(body);
        var car = new Car();
        car.setName("car");
        car.setEngine(engine);
        car.setBody(body);
        car.setTransmission(transmission);
        hbnCarRepository.create(car);
        var file = new File();
        file.setName("name");
        file.setPath("some/path");
        hbnFileRepository.create(file);
        var post = new Post();
        post.setDescription("some desc");
        post.setCreated(LocalDateTime.now());
        post.setCar(car);
        post.setFile(file);
        hbnPostRepository.create(post);
        assertThat(hbnPostRepository.findAllForTheLastDay()).isEqualTo(List.of(post));
    }

    @Test
    public void whenFindWithPhoto() {
        var engine = new Engine();
        engine.setName("engine");
        hbnEngineRepository.create(engine);
        var transmission = new Transmission();
        transmission.setName("auto");
        hbnTransmissionRepository.create(transmission);
        var body = new Body();
        body.setName("sedan");
        hbnBodyRepository.create(body);
        var car = new Car();
        car.setName("car");
        car.setEngine(engine);
        car.setBody(body);
        car.setTransmission(transmission);
        hbnCarRepository.create(car);
        var file = new File();
        file.setName("name");
        file.setPath("some/path");
        hbnFileRepository.create(file);
        var post = new Post();
        post.setDescription("some desc");
        post.setCreated(LocalDateTime.now());
        post.setCar(car);
        post.setFile(file);
        hbnPostRepository.create(post);
        assertThat(hbnPostRepository.findWithPhoto()).isEqualTo(List.of(post));
    }

    @Test
    public void whenFindByModel() {
        var engine = new Engine();
        engine.setName("engine");
        hbnEngineRepository.create(engine);
        var transmission = new Transmission();
        transmission.setName("auto");
        hbnTransmissionRepository.create(transmission);
        var body = new Body();
        body.setName("sedan");
        hbnBodyRepository.create(body);
        var car = new Car();
        car.setName("car");
        car.setEngine(engine);
        car.setBody(body);
        car.setTransmission(transmission);
        hbnCarRepository.create(car);
        var file = new File();
        file.setName("name");
        file.setPath("some/path");
        hbnFileRepository.create(file);
        var post = new Post();
        post.setDescription("some desc");
        post.setCreated(LocalDateTime.now());
        post.setCar(car);
        post.setFile(file);
        hbnPostRepository.create(post);
        assertThat(hbnPostRepository.findByModel("car")).isEqualTo(List.of(post));
    }
}