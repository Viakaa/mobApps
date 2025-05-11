package com.example.homework5

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlinx.coroutines.runBlocking

class CheckEmailAvailabilityUseCaseTest {

    private val checkEmailAvailabilityUseCase = CheckEmailAvailabilityUseCase()

    @Test
    fun `check if email is valid`() = runBlocking {
        val result = checkEmailAvailabilityUseCase.execute("wrongemail.com")
        assertTrue(result is EmailCheckResult.Invalid)
        assertEquals("Email is not valid", (result as EmailCheckResult.Invalid).reason)
    }

    @Test
    fun `check if email is taken`() = runBlocking {
        val result = checkEmailAvailabilityUseCase.execute("taken@example.com")
        assertEquals(EmailCheckResult.Unavailable, result)
    }

    @Test
    fun `check if email is free`() = runBlocking {
        val result = checkEmailAvailabilityUseCase.execute("free@example.com")
        assertEquals(EmailCheckResult.Available, result)
    }
}