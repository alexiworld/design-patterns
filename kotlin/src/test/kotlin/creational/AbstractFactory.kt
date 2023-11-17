package creational

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

interface DataSource

class DatabaseDataSource: DataSource

class NetworkDataSource: DataSource

abstract class DataSourceFactory {
    abstract fun makeDataSource(): DataSource

    companion object {
        inline fun <reified T: DataSource> createFactory(): DataSourceFactory =
            when(T::class) {
                DatabaseDataSource::class -> DatabaseFactory()
                NetworkDataSource::class -> NetworkFactory()
                else -> throw IllegalArgumentException()
            }

    }

}

class NetworkFactory: DataSourceFactory() {
    override fun makeDataSource(): DataSource = NetworkDataSource()
}

class DatabaseFactory: DataSourceFactory() {
    override fun makeDataSource(): DataSource = DatabaseDataSource()
}

class AbstractFactoryTest {

    @Test
    fun atest() {
        val dataSourceFactory: DataSourceFactory = DataSourceFactory.createFactory<DatabaseDataSource>()
        val dataSource = dataSourceFactory.makeDataSource()
        println("Created datasource $dataSource")

        Assertions.assertThat(dataSource).isInstanceOf(DatabaseDataSource::class.java)
    }

}

interface Developer {
    fun writeCode() { println("The code is ready!")}
}

class JavaDeveloper: Developer
class KotlinDeveloper: Developer

abstract class DeveloperAgency {
    abstract fun provideDeveloper(): Developer

    companion object {
        inline fun <reified T: Developer> createAgency(): DeveloperAgency =
            when(T::class) {
                JavaDeveloper::class -> JavaDeveloperAgency()
                KotlinDeveloper::class -> KotlinDeveloperAgency()
                else -> throw IllegalArgumentException()
            }
    }
}

class JavaDeveloperAgency: DeveloperAgency() {
    override fun provideDeveloper(): Developer = JavaDeveloper()
}

class KotlinDeveloperAgency: DeveloperAgency() {
    override fun provideDeveloper(): Developer = KotlinDeveloper()
}

class DeveloperAgencyTest {

    @Test
    fun atest() {
        val devAgency : DeveloperAgency = DeveloperAgency.createAgency<KotlinDeveloper>()
        val dev = devAgency.provideDeveloper()
        println("Agency provided $dev")
        dev.writeCode()
    }

}