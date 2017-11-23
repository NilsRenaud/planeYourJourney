package com.nrenaud.pyj

import com.nrenaud.pyj.routers.Routers
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.support.beans
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories

@EnableReactiveMongoRepositories
@SpringBootApplication
class Application

val webContext = beans {
    bean {
        val routers = ref<Routers>()
        routers.markerRouter()
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args) {
        addInitializers(webContext)
    }
}
