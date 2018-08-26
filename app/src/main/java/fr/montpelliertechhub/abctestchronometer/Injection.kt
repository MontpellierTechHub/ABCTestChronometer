package fr.montpelliertechhub.abctestchronometer

import android.content.Context
import fr.montpelliertechhub.abctestchronometer.repository.ABTestRepository

/**
 * Created by Hugo Gresse on 22/08/2018.
 */

object Injection {
    fun provideABTestsRepository(context: Context): ABTestRepository {
        return ABTestRepository.getInstance(context)
    }
}