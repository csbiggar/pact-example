package consumer

import au.com.dius.pact.consumer.*
import au.com.dius.pact.consumer.model.MockProviderConfig
import au.com.dius.pact.core.model.RequestResponsePact
import com.github.kittinunf.fuel.httpGet
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ContractTest {

    @Test
    fun `test something`() {

        val petStoreInventoryCall = ConsumerPactBuilder
            .consumer("carolyn.pretend-consumer")
            .hasPactWith("carolyn.google")
            .given("google exists")
            .uponReceiving("a request to google homepage")
            .path("/")
            .method("GET")

        val inventoryPact = petStoreInventoryCall
            .willRespondWith()
            .status(200)
            .headers(mapOf("Content-Type" to "text/html"))
            .toPact()

        val result = inventoryPact.runTest {
            val (_, response, _) =
                "http://localhost:6060/"
                    .httpGet()
                    .response()

            assertThat(response.statusCode).isEqualTo(200)
        }

        assertThat(result).isEqualTo(PactVerificationResult.Ok())
    }
}

private fun RequestResponsePact.runTest(testAndAssertions: () -> Unit): PactVerificationResult {
    val config = MockProviderConfig.httpConfig(port = 6060)
    return runConsumerTest(this, config, object : PactTestRun<Nothing?> {
        override fun run(mockServer: MockServer, context: PactTestExecutionContext?): Nothing? {
            testAndAssertions()
            return null
        }
    })
}
