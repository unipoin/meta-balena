SUMMARY = "Linux kernel headers test"
DESCRIPTION = "This recipe tests generated kernel headers by running a simple hello-world compile test"
SECTION = "devel/kernel"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://example_module/hello.c \
           file://example_module/Makefile \
           file://Dockerfile"

inherit kernel-arch

# Derived from kernel-arch.bbclass
valid_debian_tuple = "i386 x86 arm aarch64"

def map_DEBIAN_TUPLE(a, d):
    import re

    valid_debian_tuple = d.getVar('valid_debian_tuple').split()

    if re.match('x86', a):
        return 'x86_64-linux-gnu-'
    elif re.match('arm64', a):
        return 'aarch64-linux-gnu-'
    elif re.match('arm', a):
        return 'arm-linux-gnueabi-'
    else:
        bb.error("cannot map '%s' to a debian tuple" % a)

DEBIAN_TUPLE ?= "${@map_DEBIAN_TUPLE(d.getVar('ARCH'), d)}"

do_compile() {
    rm -rf ${B}/work
    mkdir -p ${B}/work
    cp ${DEPLOY_DIR_IMAGE}/kernel_modules_headers.tar.gz ${B}/work
    cp "${WORKDIR}"/Dockerfile ${B}/work/
    cp -r "${WORKDIR}"/example_module ${B}/work/

    IMAGETAG="${PN}:$(date +%s)"
    DOCKER_API_VERSION=1.24 docker build --tag ${IMAGETAG} --build-arg kernel_arch=${ARCH} --build-arg cross_compile_prefix=${DEBIAN_TUPLE} ${B}/work
    DOCKER_API_VERSION=1.24 docker rmi "$IMAGETAG"
}

# Explicitly depend on the do_deploy step as we use the deployed artefacts. DEPENDS doesn't cover that
do_compile[depends] += "kernel-devsrc:do_deploy"
do_compile[network] = "1"
