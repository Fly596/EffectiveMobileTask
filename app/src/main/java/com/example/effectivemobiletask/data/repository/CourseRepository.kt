package com.example.effectivemobiletask.data.repository

import android.util.Log
import com.example.effectivemobiletask.data.Course
import com.example.effectivemobiletask.data.local.CourseDao
import com.example.effectivemobiletask.data.local.CourseEntity
import com.example.effectivemobiletask.data.network.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

// Интерфейс репозитория, определяющий контракт для работы с данными курсов.
// Абстрагирует источники данных (сеть, база данных) от ViewModel.
interface CourseRepository {
    // Получает поток (Flow) со списком курсов из локальной БД, с возможностью
    // сортировки.
    fun fetchCourses(isAsc: Boolean? = null): Flow<List<Course>>

    // Получает один курс по его ID.
    suspend fun getCourseById(id: Int): Course?

    // Запускает принудительное обновление данных из сети и сохранение их в БД.
    suspend fun refreshCourses()

    // Устанавливает/снимает лайк (статус "избранное") для курса.
    suspend fun setLike(id: Int)

    // Получает поток (Flow) только с избранными курсами.
    fun getFavoritesCourses(): Flow<List<Course>>
}

// Реализация репозитория.
class CourseRepositoryImpl
@Inject
constructor(
    // Источник данных из локальной базы (Room DAO).
    private val localDataSource: CourseDao,
    // Источник данных из сети.
    private val networkRepository: NetworkRepository,
) : CourseRepository {

    // Обновляет данные в локальной БД, запрашивая их из сети.
    override suspend fun refreshCourses() {
        // Выполняется в фоновом потоке для I/O операций.
        withContext(Dispatchers.IO) {
            try {
                // Загружаем данные из сети.
                val networkCourses = networkRepository.loadCourses()
                // Маппим (преобразуем) сетевые модели в сущности для БД.
                val courseEntities = networkCourses.courses.map { it.toEntity() }

                // Сохраняем полученные сущности в базу данных.
                localDataSource.upsertAll(courseEntities)
            } catch (e: Exception) {
                // Логируем ошибку, если обновление не удалось.
                Log.e("Courses repository", "failed to refresh", e)
            }
        }
    }

    // Получение данных из локальной БД в виде потока.
    override fun fetchCourses(isAsc: Boolean?): Flow<List<Course>> {
        val courseFlow: Flow<List<CourseEntity>> =
            when (isAsc) {
                // Вызываем метод DAO, который сортирует на стороне БД.
                true -> localDataSource.getCoursesSortByPublishDate(isAsc = true)
                false -> localDataSource.getCoursesSortByPublishDate(isAsc = false)
                // Если сортировка не нужна, получаем все как есть.
                null -> localDataSource.getAllCourses()
            }
        return courseFlow.map { entities -> entities.toDomain() }
        // Получаем Flow сущностей из БД и преобразуем его в Flow доменных
        // моделей.
        /*        return localDataSource.getAllCourses().map { entities ->
            // Маппим сущности БД в доменные модели для UI.
            val domainCourses = entities.toDomain()

            // Применяем сортировку на стороне клиента, если указан параметр.
            when (isAsc) {
                true -> domainCourses.sortedBy { it.publishDate }
                false -> domainCourses.sortedByDescending { it.publishDate }
                // Если сортировка не задана, возвращаем как есть.
                null -> domainCourses
            }
        }*/
    }

    // Получает один курс по ID из локальной БД.
    override suspend fun getCourseById(id: Int): Course? {
        // Выполняем в I/O потоке.
        return withContext(Dispatchers.IO) {
            // Находим сущность и маппим ее в доменную модель.
            localDataSource.getCourseById(id)?.toDomain()
        }
    }

    // Обновляет статус "избранное" в БД.
    override suspend fun setLike(id: Int) {
        // Вызываем соответствующий метод DAO.
        localDataSource.updateFavouriteStatus(id)
    }

    // Получает поток избранных курсов из БД.
    override fun getFavoritesCourses(): Flow<List<Course>> {
        // Получаем Flow сущностей и маппим его в Flow доменных моделей.
        return localDataSource.getFavoritesCourses().map { it.toDomain() }
    }
}
