package org.elis.coreproject.config;

import org.elis.coreproject.model.Sensore;
import org.elis.coreproject.model.dto.MisurazioneDTO;
import org.elis.coreproject.service.SensoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@Configuration
@EnableScheduling
@Slf4j
public class CronConfiguration {

    @Autowired
    private SensoreService sensoreService;

    /**
     * This method is used to generate random data for the sensors.
     * Cron job executed every second
     */
    // @Scheduled(cron = "* * * * * *")
    public void insertSensoreRilevazione() {
        // Creo misurazione da inserire.
        MisurazioneDTO misurazioneDto = new MisurazioneDTO();

        // Ottengo lista id di tutti i sensori
        List<Sensore> sensori = sensoreService.findAll();

        // Se non ci sono sensori non posso inserire misurazioni.
        if (sensori.isEmpty()) {
            log.info("Nessun sensore presente, non posso inserire misurazioni");
            return;
        }

        // Randomizzo id sensore
        int randomSensore = (int) (Math.random() * sensori.size());

        // Randomizzo misurazione.
        float randomMisurazione = (float) (Math.random() * 100);

        misurazioneDto.setMisurazione(randomMisurazione);
        misurazioneDto.setIdSensore(sensori.get(randomSensore).getId());

        if (sensoreService.nuovaRilevazione(misurazioneDto).getStatusCode() == HttpStatus.OK) {
            log.info("Successfully executed the cron job. | Inserted Rilevazione: " + misurazioneDto);
        } else {
            log.error("Error executing the cron job. | Not inserted Rilevazione: " + misurazioneDto);
        }
    }
}
