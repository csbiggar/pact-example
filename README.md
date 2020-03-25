# Prerequisites

1. JVM 11+
2. Credentials for the public pact broker https://test.pact.dius.com.au/

# Notes

Consumer and provider test examples both here for convenience

**Consumer** carolyn.pretend-consumer

**Provider** carolyn.google

There may be a pact already on the broker for this, if you want to start from scratch delete this via the broker first

#### Before you start

    export PUBLIC_PACT_USERNAME=<your username> 
    export PUBLIC_PACT_PASSWORD=<your password>

#### Get a pact into the broker to test with
1. Comment out `ProviderVerificationTest` (it will fail if there are no pacts on the broker with provider `carolyn.pretend-consumer`
2. Run Consumer test and publish pact to broker:

        ./gradlew clean build pactPublish
    
3. You should then see that the pact has been uploaded to the broker, and not yet verified
    ![consumer-pact-uploaded]

#### Run provider test with runtime error and publish
1. Comment back in `ProviderVerificationTest` if you had it commented out for the above. Make sure `@State("google exists")` remains commented out - this will force the error.
2. Run Provider verification test and publish results to broker:

        ./gradlew clean build -Dpact.verifier.publishResults=true
        
   This will fail with
   
        provider.ProviderVerificationTest > pactVerificationTestTemplate$pact_example(PactVerificationContext)[1] FAILED
            au.com.dius.pact.provider.junit.MissingStateChangeMethod

   
**Expected result**

Result displays as "not run" or "problem running test" or similar on pact broker - something to make
it clear that a verification has not taken place

**Actual result**

Result shows as verified on broker:

![incorrect-successfully-verified-flag]
     
 



[consumer-pact-uploaded]: consumer-pact-uploaded.png
[incorrect-successfully-verified-flag]: incorrect-successfully-verified-flag.png