package ke.don.resources

import ski.shared.resources.generated.resources.*


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

        //People
        val IAN = Res.drawable.ian_dooley
        val IVANA = Res.drawable.ivana_cajina
        val RAFAELLA = Res.drawable.rafaella_mendes
        val ANDROID_ROBOT = Res.drawable.android_head_3D
    }

    object Videos {
        /** Path of the bundled sample video, relative to the resources root. Resolve it with `resolveVideoUriForPlayer`. */
        const val SAMPLE_VIDEO_PATH = "files/sample_video.mov"
    }
}