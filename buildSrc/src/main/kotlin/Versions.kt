object Versions {
    const val androidTargetSdkVersion = 30
    const val androidCompileSdkVersion = 30
    const val androidBuildToolsVersion = "30.0.1"
    const val androidMinSdkVersion = 24
    private const val versionMajor = 1
    private const val versionMinor = 0
    private const val versionPatch = 0
    private const val versionOffset = 0
    const val androidVersionCode =
        (versionMajor * 10000 + versionMinor * 100 + versionPatch) * 100 + versionOffset

    const val androidVersionName = "$versionMajor.$versionMinor.$versionPatch"
}
