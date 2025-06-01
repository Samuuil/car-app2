import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import com.carapp.backend.service.CarSpecImportService

@Component
class CarSpecImporterRunner(
    private val carSpecImportService: CarSpecImportService
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        println(">>> CarSpecImporterRunner started <<<")
        val trimIds = listOf(21220, 21221, 21218, 21219, 21167, 21163, 21165, 21166, 21168, 21169,
            21228, 21162, 21164, 21151, 21152, 21145, 21146, 21148, 21149, 21150,
            21144, 21147, 21174, 21176, 21175, 21177, 21179, 21178, 21233, 21234,
            21230, 21133, 21141, 21129, 21138, 21130, 21139, 21135, 21136, 21137,
            21134, 21143, 21131, 21140, 21132, 21142, 21196, 21194, 21195, 21197,
            21198, 21199, 21157, 21158, 21161, 21155, 21156, 21159, 21153, 21154,
            21160, 21224, 21225, 21227, 21222, 21223, 21226, 21200, 21201, 21202,
            21203, 21206, 21204, 21205, 21207, 21208, 21209, 21210, 21211, 21212,
            21193, 21190, 21187, 21188, 21189, 21191, 21192, 21235, 21236, 21171,
            21170, 21172, 21173, 21213, 21214, 21229, 21182, 21180, 21181, 21232)
        carSpecImportService.importSpecs(trimIds)
        println("Car specs imported!")
    }
}