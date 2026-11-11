package ke.don.demos

import ke.don.resources.generated.Res

/** Bundled resources are served by URL on the web. Remote URLs pass through unchanged. */
actual suspend fun resolveVideoUriForPlayer(videoUri: String): String {
    if (videoUri.startsWith("http://") || videoUri.startsWith("https://")) return videoUri
    return Res.getUri(videoUri)
}
