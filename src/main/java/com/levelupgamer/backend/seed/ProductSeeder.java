package com.levelupgamer.backend.seed;

import com.levelupgamer.backend.models.Product;
import com.levelupgamer.backend.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductSeeder implements CommandLineRunner {

    private final ProductRepository repo;

    @Override
    public void run(String... args) {
        // Si ya hay productos, no hacer nada (para no duplicar cada vez que levantas la app)
        if (repo.count() > 0) return;

        Product p1 = new Product();
        p1.setTitle("Xbox Series S");
        p1.setDescription("Velocidad y rendimiento de última generación a un precio excelente.");
        p1.setCategory("Consolas");
        p1.setPrice(299990);
        p1.setImgSource("/img/seriesS.png");

        Product p2 = new Product();
        p2.setTitle("Xbox Series X");
        p2.setDescription("Consola más potente de Microsoft con 1TB de almacenamiento.");
        p2.setCategory("Consolas");
        p2.setPrice(629990);
        p2.setImgSource("/img/seriesX.png");

        Product p3 = new Product();
        p3.setTitle("PC Gamer RGB");
        p3.setDescription("Ryzen 7, RTX 4070, 32GB RAM. Ideal para gaming extremo.");
        p3.setCategory("Computadores");
        p3.setPrice(1199990);
        p3.setImgSource("/img/pcnoBG.png");

        Product p4 = new Product();
        p4.setTitle("Teclado Mecánico RGB");
        p4.setDescription("Switches rojos ultra rápidos, ideal para eSports.");
        p4.setCategory("Periféricos");
        p4.setPrice(89990);
        p4.setImgSource("/img/tecladonoBG.png");

        Product p5 = new Product();
        p5.setTitle("PlayStation 5");
        p5.setDescription("Consola de nueva generación con gráficos 4K y mando DualSense.");
        p5.setCategory("Consolas");
        p5.setPrice(649990);
        p5.setImgSource("/img/ps5noBG.png");

        Product p6 = new Product();
        p6.setTitle("Mouse Gamer Logitech G Pro");
        p6.setDescription("Sensor HERO 25K, diseño ligero y preciso.");
        p6.setCategory("Periféricos");
        p6.setPrice(69990);
        p6.setImgSource("/img/mouseG.png");

        Product p7 = new Product();
        p7.setTitle("Auriculares HyperX Cloud II");
        p7.setDescription("Sonido envolvente 7.1 y micrófono desmontable.");
        p7.setCategory("Periféricos");
        p7.setPrice(79990);
        p7.setImgSource("/img/audifonos.png");

        repo.saveAll(List.of(p1, p2, p3, p4, p5, p6, p7));
    }
}
