package dev.lavalink.youtube.clients;

import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException.Severity;
import com.sedmelluq.discord.lavaplayer.tools.io.HttpInterface;
import com.sedmelluq.discord.lavaplayer.track.AudioItem;
import dev.lavalink.youtube.YoutubeAudioSourceManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AndroidTestsuiteWithThumbnail extends AndroidWithThumbnail {
    @Override
    protected @NotNull ClientConfig getBaseClientConfig(@NotNull HttpInterface httpInterface) {
        return AndroidTestsuite.BASE_CONFIG.copy();
    }

    @Override
    @NotNull
    public String getIdentifier() {
        return AndroidTestsuite.BASE_CONFIG.getName();
    }

    @Override
    public boolean canHandleRequest(@NotNull String identifier) {
        // loose check to avoid loading mixes/playlists.
        return !identifier.contains("list=") && super.canHandleRequest(identifier);
    }

    @Override
    public AudioItem loadMix(@NotNull YoutubeAudioSourceManager source,
                             @NotNull HttpInterface httpInterface,
                             @NotNull String mixId,
                             @Nullable String selectedVideoId) {
        // Considered returning null but an exception makes it clearer as to why a mix couldn't be loaded,
        // assuming someone tries to only register this client with the source manager.
        // Also, an exception will halt further loading so other source managers won't be queried.
        // N.B. This client genuinely cannot load mixes for whatever reason. You can get the mix metadata
        // but there are no videos in the response JSON. Weird.
        throw new FriendlyException("This client cannot load mixes", Severity.COMMON,
            new RuntimeException("ANDROID_TESTSUITE cannot be used to load mixes"));
    }

    @Override
    public AudioItem loadPlaylist(@NotNull YoutubeAudioSourceManager source,
                                  @NotNull HttpInterface httpInterface,
                                  @NotNull String playlistId,
                                  @Nullable String selectedVideoId) {
        // Similar to mixes except server returns status code 500 when trying to load playlists.
        throw new FriendlyException("This client cannot load playlists", Severity.COMMON,
            new RuntimeException("ANDROID_TESTSUITE cannot be used to load playlists"));
    }
}
