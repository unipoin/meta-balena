require go-common.inc

GO_BASEVERSION = "1.12"
GO_MINOR = ".17"
PV .= "${GO_MINOR}"
FILESEXTRAPATHS:prepend := "${FILE_DIRNAME}/go-${GO_BASEVERSION}:"

LIC_FILES_CHKSUM = "file://LICENSE;md5=5d4950ecb7b26d2c5e4e7b4e0dd74707"

SRC_URI += "\
    file://0001-allow-CC-and-CXX-to-have-multiple-words.patch \
    file://0002-cmd-go-make-content-based-hash-generation-less-pedan.patch \
    file://0003-allow-GOTOOLDIR-to-be-overridden-in-the-environment.patch \
    file://0004-ld-add-soname-to-shareable-objects.patch \
    file://0005-make.bash-override-CC-when-building-dist-and-go_boot.patch \
    file://0006-cmd-dist-separate-host-and-target-builds.patch \
    file://0007-cmd-go-make-GOROOT-precious-by-default.patch \
    file://0008-use-GOBUILDMODE-to-set-buildmode.patch \
"
SRC_URI:append:libc-musl = " file://0009-ld-replace-glibc-dynamic-linker-with-musl.patch"

SRC_URI[main.md5sum] = "6b607fc795391dc609ffd79ebf41f080"
SRC_URI[main.sha256sum] = "de878218c43aa3c3bad54c1c52d95e3b0e5d336e1285c647383e775541a28b25"
