package com.hybrid.appointment.core.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hybrid.appointment.data.local.database.dao.AppointmentDao
import com.hybrid.appointment.data.local.database.entities.AppointmentEntity

@Database(entities = [AppointmentEntity::class], version = 1)
abstract class AppointmentDatabase: RoomDatabase() {
    abstract fun getAppointmentDao(): AppointmentDao
}