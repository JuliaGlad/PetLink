package petlink.android.feature_community_ui_news_details.fragment.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import petlink.android.core_mvi.MviActor
import petlink.android.core_mvi.asyncAwait
import petlink.android.core_mvi.runCatchingNonCancellation
import petlink.android.feature_community_core.CommunitiesTypeTag
import petlink.android.feature_community_domain.usecase.GetNewsCommunityByIdUseCase
import petlink.android.feature_community_domain.usecase.SubscribeToNewsCommunityUseCase
import petlink.android.feature_community_domain.usecase.UnsubscribeFromNewsCommunityUseCase
import petlink.android.feature_community_domain.usecase.UpdateNewsCommunityAvatarUseCase
import petlink.android.feature_community_domain.usecase.UpdateNewsCommunityBackgroundUseCase
import petlink.android.feature_community_ui_news_details.fragment.mapper.toUi

class CommunityDetailsActor(
    private val getNewsCommunityByIdUseCase: GetNewsCommunityByIdUseCase,
    private val subscribeToNewsCommunityUseCase: SubscribeToNewsCommunityUseCase,
    private val unsubscribeFromNewsCommunityUseCase: UnsubscribeFromNewsCommunityUseCase,
    private val updateNewsCommunityAvatarUseCase: UpdateNewsCommunityAvatarUseCase,
    private val updateNewsCommunityBackgroundUseCase: UpdateNewsCommunityBackgroundUseCase
) : MviActor<
        CommunityDetailsPartialState,
        CommunityDetailsIntent,
        CommunityDetailsState,
        CommunityDetailsEffect>() {
    override fun resolve(
        intent: CommunityDetailsIntent,
        state: CommunityDetailsState
    ): Flow<CommunityDetailsPartialState> =
        when (intent) {
            is CommunityDetailsIntent.GetCommunityDetails -> when (intent.communityTypeTag) {
                CommunitiesTypeTag.NewsTag -> getNewsCommunityData(intent.id)
                CommunitiesTypeTag.PhotosTag -> TODO()
                CommunitiesTypeTag.QuestionTag -> TODO()
            }

            is CommunityDetailsIntent.Subscribe -> when (intent.communityTypeTag) {
                CommunitiesTypeTag.NewsTag -> subscribeToNewsCommunity(intent.id)
                CommunitiesTypeTag.PhotosTag -> TODO()
                CommunitiesTypeTag.QuestionTag -> TODO()
            }

            is CommunityDetailsIntent.Unsubscribe -> when (intent.communityTypeTag) {
                CommunitiesTypeTag.NewsTag -> unsubscribeFromNewsCommunity(intent.id)
                CommunitiesTypeTag.PhotosTag -> TODO()
                CommunitiesTypeTag.QuestionTag -> TODO()
            }

            is CommunityDetailsIntent.UpdateAvatar -> when (intent.communityTypeTag) {
                CommunitiesTypeTag.NewsTag -> updateAvatar(
                    communityId = intent.id,
                    newUri = intent.uri
                )

                CommunitiesTypeTag.PhotosTag -> TODO()
                CommunitiesTypeTag.QuestionTag -> TODO()
            }

            is CommunityDetailsIntent.UpdateBackground -> when (intent.communityTypeTag) {
                CommunitiesTypeTag.NewsTag -> updateBackground(
                    communityId = intent.id,
                    newUri = intent.uri
                )
                CommunitiesTypeTag.PhotosTag -> TODO()
                CommunitiesTypeTag.QuestionTag -> TODO()
            }
        }

    private fun updateBackground(communityId: String, newUri: String) =
        flow {
            runCatching {
                updateBackgroundUseCase(communityId, newUri)
            }.fold(
                onSuccess = { data ->
                    emit(CommunityDetailsPartialState.BackgroundUpdated(newUri))
                },
                onFailure = { throwable ->
                    emit(CommunityDetailsPartialState.Error(throwable))
                }
            )
        }

    private fun updateAvatar(communityId: String, newUri: String) =
        flow {
            runCatching {
                updateAvatarUseCase(communityId, newUri)
            }.fold(
                onSuccess = { data ->
                    emit(CommunityDetailsPartialState.AvatarUpdated(newUri))
                },
                onFailure = { throwable ->
                    emit(CommunityDetailsPartialState.Error(throwable))
                }
            )
        }

    private suspend fun updateBackgroundUseCase(communityId: String, newUri: String) =
        runCatchingNonCancellation {
            asyncAwait({
                updateNewsCommunityBackgroundUseCase.invoke(
                    communityId = communityId,
                    newUri = newUri
                )
            }) { it }
        }.getOrThrow()

    private suspend fun updateAvatarUseCase(communityId: String, newUri: String) =
        runCatchingNonCancellation {
            asyncAwait({
                updateNewsCommunityAvatarUseCase.invoke(
                    communityId = communityId,
                    newUri = newUri
                )
            }) { it }
        }.getOrThrow()

    private fun subscribeToNewsCommunity(communityId: String) =
        flow {
            runCatching {
                subscribeToNewsCommunityUseCase(communityId)
            }.fold(
                onSuccess = { data ->
                    emit(CommunityDetailsPartialState.Subscribed)
                },
                onFailure = { throwable ->
                    emit(CommunityDetailsPartialState.Error(throwable))
                }
            )
        }

    private fun unsubscribeFromNewsCommunity(communityId: String) =
        flow {
            runCatching {
                unsubscribeFromNewsCommunityUseCase(communityId)
            }.fold(
                onSuccess = { data ->
                    emit(CommunityDetailsPartialState.Unsubscribed)
                },
                onFailure = { throwable ->
                    emit(CommunityDetailsPartialState.Error(throwable))
                }
            )
        }

    private suspend fun unsubscribeFromNewsCommunityUseCase(communityId: String) =
        runCatchingNonCancellation {
            asyncAwait({
                unsubscribeFromNewsCommunityUseCase.invoke(communityId)
            }) { it }
        }.getOrThrow()

    private suspend fun subscribeToNewsCommunityUseCase(communityId: String) =
        runCatchingNonCancellation {
            asyncAwait({
                subscribeToNewsCommunityUseCase.invoke(communityId)
            }) { it }
        }.getOrThrow()

    private fun getNewsCommunityData(communityId: String) =
        flow<CommunityDetailsPartialState> {
            runCatching {
                loadNewsCommunityById(communityId)
            }.fold(
                onSuccess = { data ->
                    emit(CommunityDetailsPartialState.DataLoaded(data))
                },
                onFailure = { throwable ->
                    emit(CommunityDetailsPartialState.Error(throwable))
                }
            )
        }

    private suspend fun loadNewsCommunityById(communityId: String) =
        runCatchingNonCancellation {
            asyncAwait(
                { getNewsCommunityByIdUseCase.invoke(communityId) }
            ) { data ->
                data.toUi()
            }
        }.getOrThrow()

}