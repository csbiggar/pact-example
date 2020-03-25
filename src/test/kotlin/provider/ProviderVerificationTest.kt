package provider

import au.com.dius.pact.provider.junit.Provider
import au.com.dius.pact.provider.junit.State
import au.com.dius.pact.provider.junit.loader.PactBroker
import au.com.dius.pact.provider.junit5.HttpsTestTarget
import au.com.dius.pact.provider.junit5.PactVerificationContext
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.ExtendWith

@Provider("carolyn.google")
@PactBroker(
    host = "test.pact.dius.com.au",
    scheme = "https",
    port = "443"
)
class ProviderVerificationTest {

    @BeforeEach
    fun before(context: PactVerificationContext) {
        context.target = HttpsTestTarget("www.google.com", 443, "/")
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider::class)
    internal fun pactVerificationTestTemplate(context: PactVerificationContext) {
        context.verifyInteraction()
    }


    // Add this state in to make the test pass
//    @State("google exists")
//    fun `some other state`() {
//
//    }

}