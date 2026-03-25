package com.hybrid.appointment.di

import android.content.Context
import androidx.room.Room
import com.hybrid.appointment.data.local.database.AppointmentDatabase
import com.hybrid.appointment.data.local.database.dao.AppointmentDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun providerRoom(@ApplicationContext context: Context): AppointmentDatabase{
        return Room.databaseBuilder(context, AppointmentDatabase::class.java,"Appointment").build()
    }

    @Singleton
    @Provides
    fun providerAppointmenteDao(db: AppointmentDatabase): AppointmentDao{
        return db.getAppointmentDao()
    }
}