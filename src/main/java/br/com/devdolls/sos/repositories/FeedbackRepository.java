package br.com.devdolls.sos.repositories;

import br.com.devdolls.sos.entities.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    List<Feedback> findAllByOsId(Integer ordemId);
}
