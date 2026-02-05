package com.enesincekara.config;

import com.enesincekara.entity.Complaint;
import com.enesincekara.entity.enums.EStatus;
import com.enesincekara.repository.ComplaintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final ComplaintRepository repository;
    private final Random random = new Random();


    @Override
    public void run(String... args) throws Exception {
        if (repository.count() == 60) {
            generateTestData(50);
            System.out.println(">>> 50 Adet test şikayeti oluşturuldu");
        }
    }
    
    private void generateTestData(int count) {

        List<String> categories = List.of("SU_ISLERI","YOL_FEN","TEMIZLIK","PARK_BAHCELER");
        List<UUID> userIds=List.of(
                UUID.fromString("550e8400-e29b-41d4-a716-446655440000"),
                UUID.fromString("660e8400-e29b-41d4-a716-446655441111"),
                UUID.fromString("770e8400-e29b-41d4-a716-446655442222")
        );

        for (int i = 0; i < count; i++) {
            String category = categories.get(random.nextInt(categories.size()));
            UUID authId = userIds.get(random.nextInt(userIds.size()));

            Double lat = 41.0 + (random.nextDouble() * 0.05);
            Double lon = 39.0 + (random.nextDouble() * 0.05);

            Complaint complaint = Complaint.create(
                    authId,
                    category + " Şikayeti #" + (i + 1),
                    "Bu bölgede ciddi bir sorun var, lütfen ekiplerinizi yönlendirin.",
                    category,
                    List.of("https://resim-url.com/test" + i + ".jpg"),
                    lat,
                    lon
            );
            if (random.nextBoolean()) {
                complaint.updateStatus(EStatus.RESOLVED);
            }

            repository.save(complaint);
        }

    }
}
