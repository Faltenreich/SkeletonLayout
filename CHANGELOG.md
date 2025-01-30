# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [6.0.0] - 2025-01-30

### Added

- Added property for custom mask layout

## [5.0.0] - 2023-08-06

### Changed

- Breaking: Space views will not be masked anymore

## [4.0.0] - 2020-10-18

### Added

- Added property for shimmer direction [#23](https://github.com/Faltenreich/SkeletonLayout/pull/23)
- Added property for shimmer angle

### Changed

- Breaking: Changed api for creating and applying skeletons

## [3.0.0] - 2020-10-14

### Added

- Supporting ViewPager2 [#22](https://github.com/Faltenreich/SkeletonLayout/pull/22)

## [2.1.0] - 2020-08-08

### Changed

- Supporting subclassing SkeletonLayout [#18](https://github.com/Faltenreich/SkeletonLayout/pull/18)

## [2.0.2] - 2020-06-21

### Changed

- Fixed lifecycle problem with ViewBinding [#17](https://github.com/Faltenreich/SkeletonLayout/pull/17)

## [2.0.1] - 2020-01-04

### Changed

- Fixed IllegalStateException: The specified child already has a parent [#14](https://github.com/Faltenreich/SkeletonLayout/issues/14)

## [2.0.0] - 2019-03-20

### Changed

- Migrated to androix [#4](https://github.com/Faltenreich/SkeletonLayout/pull/4)
- Fixed skeleton dimensions in RecyclerView
- Fixed crash when creating skeleton manually within ViewHolder

## [1.0.1] - 2018-06-11

### Changed

- Fixed parsing of cornerRadius from attributes
- Renamed attribute for cornerRadius into maskCornerRadius

## [1.0.0] - 2018-06-11

### Added

- Initial version