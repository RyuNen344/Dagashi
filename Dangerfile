####
#
# Danger
#
####
# reference: https://danger.systems/reference.html

# Use to ignore inline messages which lay outside a diff's range, thereby not posting them in the main comment.
github.dismiss_out_of_range_messages

# https://github.com/loadsmart/danger-android_lint
android_lint.skip_gradle_task = true
android_lint.filtering = true
android_lint_dir_pattern = "**/lint-results.xml"
Dir.glob(android_lint_dir_pattern) do |file|
  android_lint.report_file = file
  android_lint.lint(inline_mode: true)
end

# https://github.com/noboru-i/danger-checkstyle_format
checkstyle_format.base_path = Dir.pwd
detekt_dir_pattern = "**/detekt*.xml"
Dir.glob(detekt_dir_pattern) do |file|
  checkstyle_format.report file
end

# reference: https://github.com/Malinskiy/danger-jacoco
jacoco_dir_pattern = "**/jacocoMergedReport.xml"
Dir.glob(jacoco_dir_pattern) do |file|
  jacoco.report(file, fail_no_coverage_data_found: false)
end

kover_dir_pattern = "**kover/report*.xml"
Dir.glob(kover_dir_pattern) do |file|
  jacoco.report(file, fail_no_coverage_data_found: false)
end

lgtm.check_lgtm https_image_only: true
