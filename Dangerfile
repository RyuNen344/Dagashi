####
#
# Danger
#
####
# reference: https://danger.systems/reference.html

# Use to ignore inline messages which lay outside a diff's range, thereby not posting them in the main comment.
github.dismiss_out_of_range_messages

####
#
# danger-android_lint
#
####
# reference: https://github.com/loadsmart/danger-android_lint

android_lint.skip_gradle_task = true
android_lint.filtering = true

android_lint_dir_pattern = "**/build/reports/lint-results.xml"
Dir.glob(android_lint_dir_pattern) do |file|
  android_lint.report_file = file
  android_lint.lint(inline_mode: true)
end

####
#
# danger-checkstyle_format
#
####
# reference: https://github.com/noboru-i/danger-checkstyle_format

checkstyle_format.base_path = Dir.pwd

# ktlint
ktlint_dir_pattern = "**/build/reports/ktlint/*.xml"
Dir.glob(ktlint_dir_pattern) do |file|
  checkstyle_format.report file
end

lgtm.check_lgtm https_image_only: true
