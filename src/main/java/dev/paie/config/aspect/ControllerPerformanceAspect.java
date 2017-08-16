package dev.paie.config.aspect;

import java.time.LocalDateTime;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import dev.paie.entite.Performance;
import dev.paie.repository.PerformanceRepository;

@Configuration
@Aspect
public class ControllerPerformanceAspect {
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerPerformanceAspect.class);
	@Autowired
	PerformanceRepository perfRepo;

	@Around("execution(* dev.paie.web.controller.*.*(..))")
	public Object logPerf(ProceedingJoinPoint pjp) throws Throwable {
		LOGGER.debug("Début d'exécution de la méthode " + pjp.getSignature().toString());
		Performance perf = new Performance();
		Long deb = System.currentTimeMillis();
		perf.setDateHeure(LocalDateTime.now());
		perf.setNomService(pjp.getSignature().toString());
		Object resultat = pjp.proceed();
		LOGGER.debug("Fin d'exécution de la méthode");
		perf.setTempsExecution(System.currentTimeMillis() - deb);
		perfRepo.save(perf);
		return resultat;
	}
}