package com.hybrid.appointment.core.di

import android.content.Context
import androidx.room.Room
import com.hybrid.appointment.core.local.database.AppointmentDatabase
import com.hybrid.appointment.data.local.database.dao.AppointmentDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun providerRoom(context: Context): AppointmentDatabase{
        return Room.databaseBuilder(context, AppointmentDatabase::class.java,"Appointment").build()
    }

    @Singleton
    @Provides
    fun providerAppointmentDao(db: AppointmentDatabase): AppointmentDao{
        return db.getAppointmentDao()
    }
}