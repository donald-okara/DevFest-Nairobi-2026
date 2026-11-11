package ke.don.resources

import ke.don.resources.generated.*


object Resources {
    object Strings {
        val APP_NAME = Res.string.app_name
    }
    object Font {
        val GOOGLE_SANS_EXTRA_LIGHT = Res.font.google_sans_regular
        val GOOGLE_SANS_BOLD = Res.font.google_sans_bold
        /** Static Google Sans Flex instance (wght 800), the template's heading face. OFL, see GoogleSansFlex-OFL.txt. */
        val GOOGLE_SANS_FLEX_EXTRA_BOLD = Res.font.google_sans_flex_extra_bold
        /** Static Google Sans Flex instance (wght 820, ROND 100) for the balloon DEVFEST wordmark. */
        val GOOGLE_SANS_FLEX_BALLOON = Res.font.google_sans_flex_balloon
        val ROBOTO_MONO_EXTRA_LIGHT = Res.font.roboto_mono_extra_light
    }

    object Images {

        val DON = Res.drawable.don
    }

    object Videos {
        /** Path of the bundled sample video, relative to the resources root. Resolve it with `resolveVideoUriForPlayer`. */
        const val SAMPLE_VIDEO_PATH = "files/sample_video.mov"
    }
}