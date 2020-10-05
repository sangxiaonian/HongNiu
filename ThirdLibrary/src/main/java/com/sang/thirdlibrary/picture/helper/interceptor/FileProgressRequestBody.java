package com.sang.thirdlibrary.picture.helper.interceptor;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

public class FileProgressRequestBody extends RequestBody {

    private RequestBody requestBody;
    private BufferedSink mBufferedSink;

    public interface ProgressListener {
        void transferred(long current, long totle);
    }

    public static final int SEGMENT_SIZE = 2 * 1024; // okio.Segment.SIZE

    protected ProgressListener listener;
    protected String contentType;


    public FileProgressRequestBody(RequestBody requestBody, ProgressListener listener) {
        this.requestBody = requestBody;
        this.listener = listener;
    }

    protected FileProgressRequestBody() {
    }

    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {

        //如果是log日志，则直接写入
        if (sink instanceof Buffer) {
            requestBody.writeTo(sink);
            return;
        }

        try {
            if (mBufferedSink == null) {
                mBufferedSink = Okio.buffer(wrapSink(sink));
            }
            requestBody.writeTo(mBufferedSink);
            mBufferedSink.flush();
        } catch (IOException e) {
            e.printStackTrace();
            Util.closeQuietly(mBufferedSink);
        }

    }


    private long current;
    private long total;
    private Sink wrapSink(Sink sink) {
        return new ForwardingSink(sink) {

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if ( total == 0) {
                     total = contentLength();
                }
                 current += byteCount;
                if (listener!=null) {
                    listener.transferred(current, total);
                }
            }
        };
    }

}
