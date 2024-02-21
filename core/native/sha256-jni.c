#include "jni.h"
#include "sha256/sha256.h"

JNIEXPORT jbyteArray JNICALL
Java_pl_lemanski_pandaloop_AndroidSha256Encoder_encode(JNIEnv *env, jobject thiz, jbyteArray src) {
    BYTE hash[SHA256_BLOCK_SIZE];
    SHA256_CTX ctx;

    size_t len = (size_t) ((*env)->GetArrayLength(env, src));
    jboolean copied;
    jbyte* bytes = (*env)->GetByteArrayElements(env, src, &copied);

    sha256_init(&ctx);
    sha256_update(&ctx, (const BYTE *) bytes, len);
    sha256_final(&ctx, hash);

    (*env)->ReleaseByteArrayElements(env, src, bytes, JNI_ABORT);

    jbyteArray result = (*env)->NewByteArray(env, SHA256_BLOCK_SIZE);
    (*env)->SetByteArrayRegion(env, result, 0, SHA256_BLOCK_SIZE, (const jbyte *) hash);
    return result;
}