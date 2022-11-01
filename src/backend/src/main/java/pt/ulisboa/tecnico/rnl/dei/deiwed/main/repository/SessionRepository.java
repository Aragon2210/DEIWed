package pt.ulisboa.tecnico.rnl.dei.deiwed.main.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pt.ulisboa.tecnico.rnl.dei.deiwed.main.entity.Session;

@Repository
@Transactional
public interface SessionRepository extends JpaRepository<Session, Long> {

}